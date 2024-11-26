package com.pinu.domain.entities.states

import com.pinu.domain.entities.network_service.response.BookItemResponse

data class BooksState(
    val isLoading: Boolean = false,
    val searchText : String = "",
    val bookList: List<BookItemResponse> = emptyList(),
    val error: String = "",
    val bookDetail: BookItemResponse? = null,
)