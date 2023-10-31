package com.example.ebookstore

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.nio.charset.Charset
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {
    private lateinit var parentAdapter: ParentAdapter

    private val mainList = ArrayList<ParentModel>()

    private lateinit var rv : RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.black)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.black)
        title ="Book Store"

        rv = findViewById(R.id.rv)

        parentAdapter = ParentAdapter(mainList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv.layoutManager = layoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = parentAdapter

        val inputStream:InputStream=assets.open("links.json")
        val size = inputStream.available()
        val buffer=ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        val listType = object: TypeToken<List<bookModel>>() {}.type
        val parser = JsonParser()
        val jsonObject=parser.parse(json).asJsonObject
//        val genresArray=jsonObject.getAsJsonArray()
        for ((genreName, booksArray) in jsonObject.entrySet()) {
             val bookList = mutableListOf<bookModel>()
            for (bookElement in booksArray.asJsonArray) {
                val book = gson.fromJson(bookElement, bookModel::class.java)
                bookList.add(book)
            }

            mainList.add(ParentModel(genreName, bookList))
            bookList.clear()


        }








//        bookList.clear()


        parentAdapter.notifyDataSetChanged()
    }
}
