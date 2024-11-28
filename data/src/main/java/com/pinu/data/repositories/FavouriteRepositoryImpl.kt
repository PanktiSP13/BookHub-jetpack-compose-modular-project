package com.pinu.data.repositories

import com.pinu.data.network.network_apis.FavouritesAPIs
import com.pinu.domain.entities.network_service.response.BookResponse
import com.pinu.domain.repositories.FavouriteRepository
import com.pinu.domain.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(private val favouriteAPI: FavouritesAPIs) :
    FavouriteRepository {

    override suspend fun fetchFavourites(): Flow<Result<BookResponse>> {
        return NetworkUtils.safeApiCall { favouriteAPI.getFavourites() }
    }

    override suspend fun addToFavourites(bookId: Int): Flow<Result<BookResponse>> {
        return NetworkUtils.safeApiCall { favouriteAPI.addToFavourites(bookId) }
    }

    override suspend fun removeFromFavourites(bookId: Int): Flow<Result<BookResponse>> {
        return NetworkUtils.safeApiCall { favouriteAPI.removeFromFavourites(bookId) }
    }

}