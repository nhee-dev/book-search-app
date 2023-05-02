package com.nhee.booksearchapp.data.books

import com.nhee.booksearchapp.data.BookSearchResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchBooksApiService {
    @GET("v1/search/book.json")
    suspend fun getBooks(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int
    ): Response<BookSearchResults>
}