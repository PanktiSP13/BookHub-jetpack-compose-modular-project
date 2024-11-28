package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.response.BookResponse
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun fetchFavourites(): Flow<Result<BookResponse>>

    suspend fun addToFavourites(bookId: Int): Flow<Result<BookResponse>>

    suspend fun removeFromFavourites(bookId: Int): Flow<Result<BookResponse>>
}
