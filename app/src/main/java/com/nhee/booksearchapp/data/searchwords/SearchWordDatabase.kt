package com.nhee.booksearchapp.data.searchwords

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SearchWord::class), version = 1, exportSchema = false)
abstract class SearchWordDatabase : RoomDatabase() {

    abstract fun searchWordDao(): SearchWordDao

    // Hilt 쓰면서 없어진 부분
//    companion object {
//        @Volatile
//        private var INSTANCE: SearchWordDatabase? = null
//
//        fun getDatabase(context: Context): SearchWordDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    SearchWordDatabase::class.java,
//                    "search_word_database"
//                ).build()
//                INSTANCE = instance
//
//                instance
//            }
//        }
//    }
}