package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.repositories.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val bookRepo: BookRepository) : ViewModel() {

    private val _bookState = MutableStateFlow(BooksState())
    val bookState = _bookState.asStateFlow()

    fun onEvent(event: BooksEvents) {
        when (event) {
            is BooksEvents.GetBookList -> getBookList()
            is BooksEvents.OnSearchBooksByName -> getBookList(searchText = event.searchText)
            is BooksEvents.NavigateToBookDetailScreen -> getBookDetail(bookId = event.bookId)
            is BooksEvents.FetchBookDetails -> getBookDetail(bookId = event.bookId)
            else -> Unit
        }
    }


    private fun getBookList(
        searchText: String? =null,
        genre: String? = null,
        sortBy: String? = null) {

        _bookState.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {
            bookRepo.getBookList(
                searchString = searchText,
                genre = genre,
                sortBy = sortBy
            ).collect { data ->
                data.fold(onSuccess = { books ->
                    _bookState.value = _bookState.value.copy(bookList = books.data?: emptyList(), isLoading = false)
                }, onFailure = { error -> onFailure(error.message ?: "") })
            }
        }
    }

    private fun getBookDetail(bookId: Int) {

        // first set book detail data from book list and then update it with network response
        if (_bookState.value.bookList.isNotEmpty()) {
            val item = _bookState.value.bookList.filter { it.id == bookId }[0]
            _bookState.update { it.copy(selectedBookDetail = item) }
        }


        // Loader not required here
        // Latest data will be seamlessly updated in the UI layer once the API response is received.
        viewModelScope.launch {
            bookRepo.getBookDetail(bookId).collect { data ->
                data.fold(onSuccess = { bookDetail ->
                    _bookState.update { it.copy(selectedBookDetail = bookDetail) }
                }, onFailure = { error -> onFailure(error.message ?: "") })
            }
        }
    }

    private fun onFailure(errorMessage: String) {
        _bookState.update { it.copy(isLoading = false) }
        sharedViewModel.onEvent(
            SharedEvents.ShowToastMessage(
                ToastMessage(
                    type = ToastMessageType.ERROR,
                    message = errorMessage
                )
            )
        )
    }
}