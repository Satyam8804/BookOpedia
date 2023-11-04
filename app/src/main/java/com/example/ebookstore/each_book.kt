package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.Date

class each_book : AppCompatActivity() {
    private lateinit var coverImg: ImageView
    private lateinit var bookTitle: TextView
    private lateinit var authorName: TextView
    private lateinit var rating: TextView
    private lateinit var genre: TextView
    private lateinit var bookDesc: TextView
    private lateinit var readBook: Button
    private lateinit var comment: EditText
    private lateinit var addComment: Button
    private lateinit var recyclerView: RecyclerView
    private val comments = mutableListOf<CommentModel>()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var dbHelper: DatabaseHelper

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_each_book)
        initializeViews()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)

        val coverUrl = intent.getStringExtra("bookCover")
        Glide.with(this).load(coverUrl)
            .apply(
                RequestOptions()
                    .error(R.drawable.baseline_star_24)
                    .placeholder(R.mipmap.book1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            ).into(coverImg)

        bookTitle.text = intent.getStringExtra("bookTitle")
        authorName.text = intent.getStringExtra("bookAuthor")
        bookDesc.text = intent.getStringExtra("bookDesc")
        rating.text = intent.getStringExtra("rating")
        genre.text = intent.getStringExtra("genre")
        val bookLink = intent.getStringExtra("bookLink")
        title = bookTitle.text

        readBook.setOnClickListener {
            val intent = Intent(this, PdfViewer::class.java)
            intent.putExtra("pdfLink", bookLink)
            intent.putExtra("bookTitle", bookTitle.text.toString())
            startActivity(intent)
        }

        fetchCommentsFromDatabase()

        addComment.setOnClickListener {
            val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val username = "Satyam Kumar"
            val userComment = comment.text.toString()
            val date = currentDate.toString()

            if (userComment.isNotEmpty()) {
                // Create a CommentModel instance
                val newComment = CommentModel(username, userComment, date)

                // Add the comment to the RecyclerView and database
                addCommentToView(newComment)
                addNewCommentToDatabase(newComment)
                comment.text.clear()
            }
        }
    }

    private fun initializeViews() {
        coverImg = findViewById(R.id.coverImg)
        bookTitle = findViewById(R.id.bookTitle)
        authorName = findViewById(R.id.authorName)
        rating = findViewById(R.id.rating)
        genre = findViewById(R.id.genre)
        bookDesc = findViewById(R.id.bookDesc)
        readBook = findViewById(R.id.readBtn)
        comment = findViewById(R.id.comment)
        addComment = findViewById(R.id.addComment)
        recyclerView = findViewById(R.id.commentList)
        commentAdapter = CommentAdapter(this,comments)
        recyclerView.adapter = commentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("Range")
    private fun fetchCommentsFromDatabase() {
        comments.clear()
        comments.addAll(dbHelper.getAllComments())
        commentAdapter.notifyDataSetChanged()
    }

    private fun addCommentToView(newComment: CommentModel) {
        comments.add(newComment)
        commentAdapter.notifyDataSetChanged()
    }

    private fun addNewCommentToDatabase(newComment: CommentModel) {
        dbHelper.addComment(newComment)

        Toast.makeText(this,"Comment added Successfully !", Toast.LENGTH_SHORT).show()
    }
}
