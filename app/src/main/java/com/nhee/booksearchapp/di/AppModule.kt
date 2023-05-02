package com.nhee.booksearchapp.di

import android.content.Context
import androidx.room.Room
import com.nhee.booksearchapp.data.BooksRemoteDataSource
import com.nhee.booksearchapp.data.BooksRepository
import com.nhee.booksearchapp.data.SearchBooksApi
import com.nhee.booksearchapp.data.searchwords.SearchWordDao
import com.nhee.booksearchapp.data.searchwords.SearchWordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSearchWordDatabase(
        @ApplicationContext context: Context
    ) : SearchWordDatabase = Room
        .databaseBuilder(context, SearchWordDatabase::class.java, "search_word_database")
        .build()


    @Singleton
    @Provides
    fun provideSearchWordDao(
        searchWordDatabase: SearchWordDatabase
    ): SearchWordDao = searchWordDatabase.searchWordDao()

}