package com.example.ebookstore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class bookAdapter(var list:MutableList<bookModel>):RecyclerView.Adapter<bookAdapter.myDataHolder>() {
    class myDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var title: TextView = itemView.findViewById(R.id.title)
        var coverPic: ImageView = itemView.findViewById(R.id.coverPic)
        var rating : TextView = itemView.findViewById(R.id.rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myDataHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list,parent,false)

        return myDataHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: myDataHolder, position: Int) {
        val data = list[position]
        holder.title.text = data.getTitle()
        holder.coverPic.setImageResource(data.getCoverImg()!!)
        holder.rating.text = data.getRating()

    }

}