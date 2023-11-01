package com.example.ebookstore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ParentAdapter(private val parentList: List<ParentModel>):RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {
    private lateinit var childAdapter: bookAdapter
    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val viewMore:TextView = itemView.findViewById(R.id.viewMore)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.child_rv)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parent_list, parent, false)
        return ParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parentList.size
    }
    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = parentList[position]
        holder.title.text = parentItem.title
        holder.viewMore.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, GridViewActivity::class.java)

            val dataList = ArrayList<bookModel>()
            for (book in parentItem.mList) {
                dataList.add(book)
            }
            intent.putParcelableArrayListExtra("gridData", dataList)
            intent.putExtra("title",parentItem.title)

            context.startActivity(intent)
        }


        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.childRecyclerView.context,LinearLayoutManager.HORIZONTAL,false)
        childAdapter = bookAdapter(parentItem.mList)
        holder.childRecyclerView.adapter = childAdapter
    }
}