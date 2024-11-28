package com.pinu.domain.entities.events

import com.pinu.domain.entities.FavouriteDataModel

sealed interface FavouritesEvents {
    data object FetchFavourites : FavouritesEvents
    data class AddAsFavourite(val bookID: Int) : FavouritesEvents
    data class RemoveFromFavourites(val bookID: Int) : FavouritesEvents
    data class NavigateToBookDetailScreen(val item : FavouriteDataModel) : FavouritesEvents
    data class MoveToCart(val bookId : String) : FavouritesEvents
}