package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.nio.charset.Charset
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {
    private lateinit var parentAdapter: ParentAdapter

    private val mainList = ArrayList<ParentModel>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var rv : RecyclerView

    lateinit var userName : TextView
    lateinit var add : FloatingActionButton
    lateinit var upload: FloatingActionButton
    lateinit var logOut: FloatingActionButton
    var fabvisibility = false

    @SuppressLint("NotifyDataSetChanged", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MultiDex.install(this)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        title ="Book Store"

        databaseReference = FirebaseDatabase.getInstance().reference.child("books")
        userName = findViewById(R.id.userName)


        rv = findViewById(R.id.rv)

        parentAdapter = ParentAdapter(mainList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = parentAdapter


        add = findViewById(R.id.idFABAdd)
        upload = findViewById(R.id.idFABUpload)
        logOut = findViewById(R.id.idFABLogOut)


        add.setOnClickListener{
            if(!fabvisibility){
                upload.show()
                logOut.show()
                upload.visibility = View.VISIBLE
                logOut.visibility = View.VISIBLE
                add.setImageDrawable(resources.getDrawable(R.drawable.baseline_close_24))
                fabvisibility = true

            }else{
                upload.hide()
                logOut.hide()
                upload.visibility = View.GONE
                logOut.visibility = View.GONE
                add.setImageDrawable(resources.getDrawable(R.drawable.baseline_add_24))
                fabvisibility = false
            }
        }

        upload.setOnClickListener{

            val intent = Intent(this,book_add::class.java)
            startActivity(intent)
        }
        logOut.setOnClickListener{
            val intent = Intent(this,LoginPage::class.java)
            startActivity(intent)

        }


        // Attach a ValueEventListener to fetch data
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mainList.clear() // Clear the list to avoid duplicates

                for (genreSnapshot in snapshot.children) {
                    val genreName = genreSnapshot.key
                    val bookList = mutableListOf<bookModel>()

                    for (bookSnapshot in genreSnapshot.children) {
                        val book = bookSnapshot.getValue(bookModel::class.java)
                        bookList.add(bookModel(book?.title,book!!.coverImg,book.des,book.rating,book.genre,book.authorName,book.publishedYear,book.link))
                    }

                    if (genreName != null) {
                        mainList.add(ParentModel(genreName, bookList))
                    }
                }

                parentAdapter.notifyDataSetChanged() // Notify the adapter of the data change
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors here
                Log.e("Firebase", "Data retrieval failed: $error")
            }
        })
    }

    }

