package com.nhee.booksearchapp.ui.books

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nhee.booksearchapp.data.searchwords.SearchWord
import com.nhee.booksearchapp.data.searchwords.SearchWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val searchWordsRepository: SearchWordsRepository
) : ViewModel() {

    private val _searchWords = MutableLiveData<String>("")
    val searchWords: LiveData<String>
        get() = _searchWords

    fun updateSearchWords(s: Editable) {
        _searchWords.value = s.toString()
    }

    // Launching a new coroutine to save(insert) the data in a non-blocking way
    fun searchBooks() = viewModelScope.launch {
        searchWordsRepository.insert(searchWords.value!!)
    }
}