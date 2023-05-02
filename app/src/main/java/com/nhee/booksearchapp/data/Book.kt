package com.nhee.booksearchapp.data

data class Book(
    val imageUrl: String,
    val title: String,
    val author: String,
    val publisher: String,
    val discount: Int,
    val url: String
)