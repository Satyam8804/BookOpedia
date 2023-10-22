package com.example.ebookstore

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var parentAdapter: ParentAdapter

    private val mainList = ArrayList<ParentModel>()
    private val bookList = ArrayList<bookModel>()
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


        var data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 2",R.mipmap.book1,"Book 1" ,"4.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 3",R.mipmap.book1,"Book 1" ,"5.0" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 4",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 5",R.mipmap.book1,"Book 1" ,"2.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 6",R.mipmap.book1,"Book 1" ,"4.3" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.9" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"4.9" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data)

//        bookList.clear()

        mainList.add(ParentModel("Comics" , R.mipmap.book1 , bookList))

        var data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)
        data1 = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data1)

        mainList.add(ParentModel("Programming" , R.mipmap.book1 , bookList))

        parentAdapter.notifyDataSetChanged()
    }
}
