package com.nhee.booksearchapp.ui.books

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhee.booksearchapp.domain.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class BooksViewModel : ViewModel() {

    private val _searchWords = MutableLiveData<String>("")
    val searchWords: LiveData<String>
        get() = _searchWords

    fun updateSearchWords(s: Editable) {
        _searchWords.value = s.toString()
    }
}