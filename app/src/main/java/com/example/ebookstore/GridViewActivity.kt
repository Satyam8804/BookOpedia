package com.example.ebookstore

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.GridView

import androidx.core.content.ContextCompat


class GridViewActivity : AppCompatActivity() {
    lateinit var gridView : GridView
    lateinit var list: ArrayList<bookModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.black)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.black)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gridView = findViewById(R.id.gv)


        list = ArrayList()

        val gridData = intent.getParcelableArrayListExtra<bookModel>("gridData")
        if (gridData != null) {
            list.addAll(gridData)
        }
        title = intent.getStringExtra("title")
        gridView = findViewById(R.id.gv)
        val adap = gridAdapter(this, list)
        gridView.adapter = adap

    }
}
