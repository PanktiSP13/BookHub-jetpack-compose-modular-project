package com.pinu.domain.entities.states

import com.pinu.domain.entities.network_service.response.BookResponse


data class BooksState(
    val isLoading: Boolean = false,
    val searchText : String = "",
    val bookList: List<BookResponse.BookItemResponse> = emptyList(),
    val selectedBookDetail: BookResponse.BookItemResponse? = null,
)