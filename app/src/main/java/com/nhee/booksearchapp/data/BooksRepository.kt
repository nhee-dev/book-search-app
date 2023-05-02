package com.nhee.booksearchapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.nhee.booksearchapp.data.Result
import kotlinx.coroutines.flow.catch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRepository @Inject constructor(
    private val booksRemoteDataSource: BooksRemoteDataSource
) {
    fun searchBooks(clientId: String, clientSecret: String, searchWords: String, display: Int, start: Int)
    : Flow<Result<Response<BookSearchResults>>> = flow {

        emit(Result.Loading)

        booksRemoteDataSource.searchBooks(clientId, clientSecret, searchWords, display, start).collect {
            if (it.code() == 200) {
                emit(Result.Success(it))
            }
        }
    }.catch { e ->
        emit(Result.Error(e))
    }
}