package com.example.ebookstore

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class bookAdapter(var list: List<bookModel>):RecyclerView.Adapter<bookAdapter.myDataHolder>() {
    class myDataHolder(itemView: View,list:List<bookModel>) : RecyclerView.ViewHolder(itemView)
    {
        var title: TextView = itemView.findViewById(R.id.title)
        var coverPic: ImageView = itemView.findViewById(R.id.coverPic)
        var rating : TextView = itemView.findViewById(R.id.rating)

        init {
            // Add an OnClickListener to the itemView
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedBook = list[position]
                    val context = itemView.context
                    val intent = Intent(context, each_book::class.java)
                    intent.putExtra("bookTitle", clickedBook.title)
                    intent.putExtra("bookAuthor", clickedBook.authorName)
                    intent.putExtra("bookCover", clickedBook.coverImg)
                    intent.putExtra("bookDesc", clickedBook.des)
                    intent.putExtra("rating", clickedBook.rating)
                    intent.putExtra("genre", clickedBook.genre)
                    intent.putExtra("year", clickedBook.publishedYear)
                    intent.putExtra("bookLink",clickedBook.link)
                    context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myDataHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list,parent,false)
        return myDataHolder(inflater,list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: myDataHolder, position: Int) {
        val data = list[position]
        holder.title.text = data.title
        Glide.with(holder.itemView.context).load(data.coverImg).apply(
            RequestOptions()
                .error(R.drawable.baseline_star_24)
            .placeholder(R.mipmap.book1) // You can set a placeholder image
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
        ).into(holder.coverPic)

        holder.rating.text = data.rating

    }

}