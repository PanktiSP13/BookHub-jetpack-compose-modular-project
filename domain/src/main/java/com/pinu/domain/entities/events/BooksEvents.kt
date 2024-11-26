package com.pinu.domain.entities.events

sealed class BooksEvents {
   data object GetBookList : BooksEvents()
   data class NavigateToBookDetailScreen(val bookId : Int): BooksEvents()
   data class OnSearchBooksByName(val searchText: String):BooksEvents()
}