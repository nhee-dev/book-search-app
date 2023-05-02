package com.nhee.booksearchapp.data.searchwords

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWordsRepository @Inject constructor(
    private val searchWordDao: SearchWordDao
) {
    val searchWords: Flow<List<SearchWord>> = searchWordDao.getSearchWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: String) {
        val searchWord = SearchWord(
            word = word
        )
        searchWordDao.insert(searchWord)
    }
}