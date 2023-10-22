package com.example.ebookstore

data class ParentModel(
    val title: String,
    val logo: Int,
    val mList: List<bookModel>
)
{}