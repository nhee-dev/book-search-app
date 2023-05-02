package com.nhee.booksearchapp.ui.books

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhee.booksearchapp.clientId
import com.nhee.booksearchapp.clientSecret
import com.nhee.booksearchapp.data.Book
import com.nhee.booksearchapp.data.BookSearchResults
import com.nhee.booksearchapp.data.BooksRepository
import com.nhee.booksearchapp.data.searchwords.SearchWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.nhee.booksearchapp.data.Result
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val searchWordsRepository: SearchWordsRepository,
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _booksSearchResults: MutableStateFlow<Result<Response<BookSearchResults>>> =
        MutableStateFlow(Result.Uninitialized)
    val booksSearchResults
        get() = _booksSearchResults.asStateFlow()

    private val _books: MutableStateFlow<List<Book>> =
        MutableStateFlow(mutableListOf())
    val books get() = _books.asStateFlow()

    private val _searchWords = MutableLiveData<String>("")
    val searchWords: LiveData<String>
        get() = _searchWords

    fun updateSearchWords(s: Editable) {
        _searchWords.value = s.toString()
    }

    // Launching a new coroutine to save(insert) the data in a non-blocking way
    fun searchBooks() = viewModelScope.launch {
        val word = searchWords.value!!

        searchWordsRepository.insert(word)
        booksRepository.searchBooks(clientId, clientSecret, word).collectLatest {
            if (it is Result.Loading) {
                _booksSearchResults.emit(it)
            }
            else if (it is Result.Success) {
                _booksSearchResults.emit(it)
                _books.emit(it.data.body()!!.items)
            }
            else if (it is Result.Error) {
                _booksSearchResults.emit(it)
            }
        }
    }
}