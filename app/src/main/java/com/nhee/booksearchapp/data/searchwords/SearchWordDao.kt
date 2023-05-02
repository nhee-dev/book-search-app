package com.nhee.booksearchapp.data.searchwords

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchWordDao {

    @Query("SELECT * FROM search_word_table ORDER BY id DESC LIMIT 10")
    fun getSearchWords(): Flow<List<SearchWord>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: SearchWord)

    @Query("DELETE FROM search_word_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}