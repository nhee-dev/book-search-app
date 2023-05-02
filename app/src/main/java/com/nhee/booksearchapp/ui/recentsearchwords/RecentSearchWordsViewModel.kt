package com.nhee.booksearchapp.ui.recentsearchwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nhee.booksearchapp.data.searchwords.SearchWord
import com.nhee.booksearchapp.data.searchwords.SearchWordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecentSearchWordsViewModel @Inject constructor(
    private val searchWordsRepository: SearchWordsRepository
) : ViewModel() {
    /* 구성변화에도 있어야 하는 정보
        1. 최근 검색어 리스트 (Room에서 가져온 정보)
    */
    val searchWordsList: LiveData<List<SearchWord>> =
        searchWordsRepository.searchWords.asLiveData()
}