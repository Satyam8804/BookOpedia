package com.example.ebookstore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class CommentAdapter(private val context: Context, private val comments: List<CommentModel>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_comments, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.name)
        private val commentTextView: TextView = itemView.findViewById(R.id.commentText)
        private val dateTextView: TextView = itemView.findViewById(R.id.commentDate)

        fun bind(comment: CommentModel) {
            usernameTextView.text = comment.username
            commentTextView.text = comment.comment
            dateTextView.text = comment.date
        }
    }
}
