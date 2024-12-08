package com.pinu.domain.entities.events

sealed interface BooksEvents {
   data object GetBookList : BooksEvents
   data object NavigateBack : BooksEvents
   data object NavigateToCartScreen : BooksEvents
   data object ClearToastMessage : BooksEvents
   data class NavigateToBookDetailScreen(val bookId : Int): BooksEvents
   data class FetchBookDetails(val bookId : Int): BooksEvents
   data class OnSearchBooksByName(val searchText: String):BooksEvents
}