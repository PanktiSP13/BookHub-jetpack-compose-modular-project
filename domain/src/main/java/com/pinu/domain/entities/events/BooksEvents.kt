package com.pinu.domain.entities.events

sealed interface BooksEvents {
   data object IsLoading : BooksEvents
   data object GetBookList : BooksEvents
   data class NavigateToBookDetailScreen(val bookId : Int): BooksEvents
   data class OnSearchBooksByName(val searchText: String):BooksEvents
}