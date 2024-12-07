package com.pinu.domain.entities.events

sealed interface BooksEvents {
   data object NavigateBack : BooksEvents
   data object GoToCart : BooksEvents
   data object GetBookList : BooksEvents
   data object ClearToastMessage : BooksEvents
   data class NavigateToBookDetailScreen(val bookId : Int): BooksEvents
   data class FetchBookDetails(val bookId : Int): BooksEvents
   data class OnSearchBooksByName(val searchText: String):BooksEvents
   data class AddAsFavourite(val bookID: Int) : BooksEvents
   data class RemoveFromFavourites(val bookID: Int) : BooksEvents
   data class AddToCart(val bookID: Int) : BooksEvents
}