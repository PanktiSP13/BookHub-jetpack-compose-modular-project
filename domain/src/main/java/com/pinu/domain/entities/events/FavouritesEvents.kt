package com.pinu.domain.entities.events

import com.pinu.domain.entities.FavouriteDataModel

sealed class FavouritesEvents {
    data class NavigateToBookDetailScreen(val item : FavouriteDataModel) : FavouritesEvents()
    data class DeleteFavourite(val bookId : String) : FavouritesEvents()
    data class MoveToCart(val bookId : String) : FavouritesEvents()
}