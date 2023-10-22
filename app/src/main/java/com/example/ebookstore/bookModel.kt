package com.example.ebookstore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class bookModel(
    val title: String,
    val coverImg: Int,
    val desc: String,
    val rating: String,
    val genre: String,
    val authorName: String,
    val publishedYear: String
):Parcelable
