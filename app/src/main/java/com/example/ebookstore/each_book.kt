package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class each_book : AppCompatActivity() {
    lateinit var coverImg:ImageView
    lateinit var bookTitle:TextView
    lateinit var authorName:TextView
    lateinit var rating:TextView
    lateinit var genre:TextView
    lateinit var bookDesc:TextView
    lateinit var readBook :Button
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_book)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coverImg = findViewById(R.id.coverImg)

        bookTitle = findViewById(R.id.bookTitle)
        authorName = findViewById(R.id.authorName)
        rating = findViewById(R.id.rating)
        genre = findViewById(R.id.genre)
        bookDesc = findViewById(R.id.bookDesc)
        readBook = findViewById(R.id.readBtn)
           val coverurl = intent.getStringExtra("bookCover")
        Glide.with(this).load(coverurl).
        apply(
            RequestOptions()
                .error(R.drawable.baseline_star_24)
            .placeholder(R.mipmap.book1) // You can set a placeholder image
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
        ).into(coverImg)

        bookTitle.text = intent.getStringExtra("bookTitle")
        authorName.text = intent.getStringExtra("bookAuthor")
        bookDesc.text = intent.getStringExtra("bookDesc")
        rating.text = intent.getStringExtra("rating")
        genre.text = intent.getStringExtra("genre")
        val bookLink = intent.getStringExtra("bookLink")
        title = bookTitle.text

        readBook.setOnClickListener{
            val intent = Intent(this,PdfViewer::class.java)
            intent.putExtra("pdfLink",bookLink)
            intent.putExtra("bookTitle",bookTitle.text.toString())
            startActivity(intent)
        }

    }
}