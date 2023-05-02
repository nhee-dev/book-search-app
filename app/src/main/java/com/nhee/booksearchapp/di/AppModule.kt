package com.nhee.booksearchapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhee.booksearchapp.BASE_URL
import com.nhee.booksearchapp.data.books.SearchBooksApiService
import com.nhee.booksearchapp.data.searchwords.SearchWordDao
import com.nhee.booksearchapp.data.searchwords.SearchWordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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

    @Provides
    @Singleton
    fun provideGson() : Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofit(gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideSearchBooksApi(@Named("retrofit") retrofit: Retrofit): SearchBooksApiService {
        return retrofit.create(SearchBooksApiService::class.java)
    }
}