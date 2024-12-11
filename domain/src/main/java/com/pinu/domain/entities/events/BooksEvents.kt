package com.pinu.domain.entities.events

import com.pinu.domain.entities.network_service.response.BookResponse

sealed interface BooksEvents {
   data object GetBookList : BooksEvents
   data object NavigateBack : BooksEvents
   data object NavigateToCartScreen : BooksEvents
   data class NavigateToBookDetailScreen(
      val bookId: Int,
      val item: BookResponse.BookItemResponse? = null,
   ) : BooksEvents
   data class FetchBookDetails(val bookId : Int): BooksEvents
   data class OnSearchBooksByName(val searchText: String):BooksEvents
}