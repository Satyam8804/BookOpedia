package com.example.ebookstore

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ProfilePictureDialog(context: Context, private val profileImageUrl: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_picture_layout)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)

        Glide.with(context)
            .load(profileImageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(profileImageView)


    }
}
