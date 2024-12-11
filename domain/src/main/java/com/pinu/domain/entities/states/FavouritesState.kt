package com.pinu.domain.entities.states

import com.pinu.domain.entities.network_service.response.BookResponse

data class FavouritesState(
    val favouriteList: List<BookResponse.BookItemResponse> = emptyList(),
    val isLoading: Boolean = false,
)