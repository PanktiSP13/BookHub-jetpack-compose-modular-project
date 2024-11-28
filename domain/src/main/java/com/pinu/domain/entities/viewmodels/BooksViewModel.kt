package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinu.domain.entities.events.BooksEvents
import com.pinu.domain.entities.states.BooksState
import com.pinu.domain.repositories.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val bookRepo: BookRepository) : ViewModel() {

    private val _bookState = MutableStateFlow(BooksState())
    val bookState = _bookState.asStateFlow()

    fun onEvent(event: BooksEvents) {
        when (event) {

            is BooksEvents.ClearErrorMessage -> {
                _bookState.value = _bookState.value.copy(error = "")
            }
            is BooksEvents.GetBookList -> getBookList()
            is BooksEvents.OnSearchBooksByName -> getBookList(searchText = event.searchText)
            is BooksEvents.NavigateToBookDetailScreen -> getBookDetail(bookId = event.bookId)
            else -> Unit
        }
    }


    private fun getBookList(
        searchText: String? =null,
        genre: String? = null,
        sortBy: String? = null) {
        _bookState.value = _bookState.value.copy(isLoading = true)
        viewModelScope.launch {
            bookRepo.getBookList(
                searchString = searchText,
                genre = genre,
                sortBy = sortBy
            ).collect { data ->
                data.fold(onSuccess = { books ->
                    _bookState.value = _bookState.value.copy(bookList = books.data?: emptyList())
                }, onFailure = { error ->
                    _bookState.value = _bookState.value.copy(error = error.message ?: "")
                })
            }
        }
    }

    private fun getBookDetail(bookId: Int) {

        // first set book detail data from book list and then update it with network response
        val item = _bookState.value.bookList.filter { it.id == bookId }[0]
        _bookState.value = _bookState.value.copy(selectedBookDetail = item)

        viewModelScope.launch {
            bookRepo.getBookDetail(bookId).collect { data ->
                data.fold(onSuccess = { bookDetail ->
                    _bookState.value = _bookState.value.copy(selectedBookDetail = bookDetail)
                }, onFailure = { error ->
                    _bookState.value = _bookState.value.copy(error = error.message ?: "")
                })
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        //it will reset book state
        //todo check without reset it
//        _bookState.value = BooksState()
    }
}