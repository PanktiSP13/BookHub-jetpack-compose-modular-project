package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import com.pinu.domain.entities.network_service.response.CommonResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartAPIs {

    /* -------------- Cart Services --------------------- */

    @GET("/cart/items")
    suspend fun fetchCartItems(): CartItemsResponse

    @POST("/cart/add")
    suspend fun addToCart(@Body request: AddToCartRequest): CartItemsResponse

    @POST("/cart/remove/{id}")
    suspend fun removeFromCart(@Path("id") id: Int): CartItemsResponse

    @PATCH("/cart/update-quantity")
    suspend fun updateQuantity(@Body request: UpdateItemQuantityRequest): CartItemsResponse

    @GET("/cart/is-in-cart")
    suspend fun isItemInCart(@Query("bookId") bookId: Int): CartItemsResponse

    @DELETE("/cart/clear")
    suspend fun clearCart(): CartItemsResponse
}