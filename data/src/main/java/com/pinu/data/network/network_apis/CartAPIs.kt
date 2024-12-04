package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartAPIs {

    /* -------------- Cart Services --------------------- */

    @GET("api/cart/items")
    suspend fun fetchCartItems(): CartItemsResponse

    @POST("api/cart/add")
    suspend fun addToCart(@Body request: AddToCartRequest): CartItemsResponse

    @POST("api/cart/remove")
    suspend fun removeFromCart(@Query("id") id: Int): CartItemsResponse

    @PATCH("api/cart/update-quantity")
    suspend fun updateQuantity(@Body request: UpdateItemQuantityRequest): CartItemsResponse

    @DELETE("api/cart/clear")
    suspend fun clearCart(): CartItemsResponse

    // optional
    @GET("api/cart/is-in-cart")
    suspend fun isItemInCart(@Query("bookId") bookId: Int): CartItemsResponse
}