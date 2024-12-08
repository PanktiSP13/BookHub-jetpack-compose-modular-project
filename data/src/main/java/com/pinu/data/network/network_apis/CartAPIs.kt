package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.CartItemsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface CartAPIs {

    /* -------------- Cart Services --------------------- */

    @GET("api/cart/items")
    suspend fun fetchCartItems(): CartItemsResponse

    @POST("api/cart/add")
    suspend fun addToCart(@Query("id") bookId: Int, @Query("qty") quantity: Int): CartItemsResponse

    @POST("api/cart/remove")
    suspend fun removeFromCart(@Query("id") id: Int): CartItemsResponse

    @FormUrlEncoded
    @PATCH("api/cart/update-quantity")
    suspend fun updateQuantity(
        @Field("id") bookId: Int,
        @Field("qty") quantity: Int
    ): CartItemsResponse

    @POST("api/cart/clear")
    suspend fun clearCart(): CartItemsResponse

    // optional
    @GET("api/cart/is-in-cart")
    suspend fun isItemInCart(@Query("bookId") bookId: Int): CartItemsResponse
}