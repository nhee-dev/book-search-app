package com.nhee.booksearchapp.data

import com.nhee.booksearchapp.data.books.SearchBooksApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRemoteDataSource @Inject constructor(
    private val searchBooksApi: SearchBooksApiService,
) {
    suspend fun searchBooks(clientId: String, clientSecret: String, searchWords: String)
    : Flow<Response<BookSearchResults>> = flow {
        emit(searchBooksApi.getBooks(clientId, clientSecret, searchWords))
    }
}