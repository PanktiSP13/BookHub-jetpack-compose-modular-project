package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.network_service.response.BookItemResponse
import com.pinu.domain.usecases.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val useCase: BookUseCase) : ViewModel() {

    private var _bookList = MutableStateFlow<List<BookItemResponse>>(emptyList())
    var bookList = _bookList.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        getBookList()
    }

    fun getBookList(searchText: String? = "", genre: String? = null, sortBy: String? = null) {

        viewModelScope.launch {
            useCase.apiCallGetBookList(
                searchString = searchText,
                genre = genre, sortBy = sortBy
            ) { _errorMessage.value = it }
        }
    }
}