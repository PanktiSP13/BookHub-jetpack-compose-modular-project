package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.BookItemResponse
import com.pinu.domain.entities.network_service.response.CommonResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WishlistAPIs {

    /* -------------- WishList Service --------------------- */

    @GET("/wishlist")
    suspend fun getWishlist(): List<BookItemResponse>

    @POST("/wishlist/add")
    suspend fun addToWishlist(@Body bookId: String): CommonResponse

    @POST("/wishlist/remove")
    suspend fun removeFromWishlist(@Body bookId: String): CommonResponse
}
