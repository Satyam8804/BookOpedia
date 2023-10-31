package com.example.ebookstore

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class bookModel(
    val title: String?,
    val coverImg: String,
    val des: String?,
    val rating: String?,
    val genre: String?,
    val authorName: String?,
    val publishedYear: String?,
    val link: String?
):Parcelable {

}
