package com.example.ebookstore

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var bookAdapter: bookAdapter
    val bookList = ArrayList<bookModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.olive)))
        window?.statusBarColor = ContextCompat.getColor(this,R.color.olive)



        title ="Book Store"
        var rv: RecyclerView = findViewById(R.id.rv)

        bookAdapter = bookAdapter(bookList)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        rv.layoutManager = layoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = bookAdapter

        var data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);
        data = bookModel("Semester 1",R.mipmap.book1,"Book 1" ,"3.5" , "Comics","Satyam" ,"2019")
        bookList.add(data);


        bookAdapter.notifyDataSetChanged()
    }
}
