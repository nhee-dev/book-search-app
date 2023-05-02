package com.nhee.booksearchapp.data

data class BookSearchResults(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Book>
)
