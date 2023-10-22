package com.example.ebookstore

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class each_book : AppCompatActivity() {
    lateinit var coverImg:ImageView
    lateinit var bookTitle:TextView
    lateinit var authorName:TextView
    lateinit var rating:TextView
    lateinit var genre:TextView
    lateinit var bookDesc:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_book)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.black)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.black)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title =" Introduction to Python: Build a Master Comprehensive Programming"
        coverImg = findViewById(R.id.coverImg)

        bookTitle = findViewById(R.id.bookTitle)
        authorName = findViewById(R.id.authorName)
        rating = findViewById(R.id.rating)
        genre = findViewById(R.id.genre)
        bookDesc = findViewById(R.id.bookDesc)

        coverImg.setImageResource(intent.getIntExtra("bookCover",R.mipmap.book1))
        bookTitle.text = "Introduction to Python: Build a Master Comprehensive Programming"
        authorName.text = intent.getStringExtra("bookAuthor")
        bookDesc.text = intent.getStringExtra("bookDesc")
        rating.text = intent.getStringExtra("rating")
        genre.text = intent.getStringExtra("genre")

    }
}