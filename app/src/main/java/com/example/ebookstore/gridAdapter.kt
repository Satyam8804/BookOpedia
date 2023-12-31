package com.example.ebookstore

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class gridAdapter(var c: Context, var list: ArrayList<bookModel>): ArrayAdapter<bookModel>(c ,R.layout.book_list,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.book_list, parent, false)

        val item = list[position]

        // Customize your grid item view here
        val title = view.findViewById<TextView>(R.id.title)
        val coverImage= view.findViewById<ImageView>(R.id.coverPic)
        val rating = view.findViewById<TextView>(R.id.rating)

        title.text = item.title
        rating.text = item.rating
        Log.d((item.coverImg).toString(),"gwgw")
        Glide.with(c)
            .load(item.coverImg).  apply(
                RequestOptions()
                    .error(R.drawable.baseline_star_24)
                    .placeholder(R.mipmap.book1) // You can set a placeholder image
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                    )
            .into(coverImage);
            // Add an OnClickListener to the itemView
            view.setOnClickListener {

                if (position != GridView.INVALID_POSITION) {
                    val clickedBook = list[position]
                    val context = view.context
                    val intent = Intent(context, each_book::class.java)
                    intent.putExtra("bookTitle", clickedBook.title)
                    intent.putExtra("bookAuthor", clickedBook.authorName)
                    intent.putExtra("bookCover", clickedBook.coverImg)
                    intent.putExtra("bookDesc", clickedBook.des)
                    intent.putExtra("rating", clickedBook.rating)
                    intent.putExtra("genre", clickedBook.genre)
                    intent.putExtra("year", clickedBook.publishedYear)
                    intent.putExtra("bookLink" , clickedBook.link)
                    context.startActivity(intent)
                }
            }


        return view
    }
}