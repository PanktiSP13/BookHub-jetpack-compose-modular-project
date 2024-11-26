package com.pinu.domain.entities.states

import com.pinu.domain.entities.FavouriteDataModel

data class FavouritesState(
    val favouriteList : List<FavouriteDataModel> = emptyList(),
    val isLoading : Boolean = false) {
}