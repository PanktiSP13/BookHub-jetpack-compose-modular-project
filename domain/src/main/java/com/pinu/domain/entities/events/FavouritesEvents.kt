package com.pinu.domain.entities.events

sealed interface FavouritesEvents {
    data object FetchFavourites : FavouritesEvents
    data class AddAsFavourite(val bookID: Int) : FavouritesEvents
    data class RemoveFromFavourites(val bookID: Int) : FavouritesEvents
    data class NavigateToBookDetailScreen(val bookID: Int ) : FavouritesEvents
    data class MoveToCart(val bookId : Int) : FavouritesEvents
}