package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addToCart(cartRequest: AddToCartRequest): Flow<Result<CartItemsResponse>>

    suspend fun removeItemFromCart(bookId: Int): Flow<Result<CartItemsResponse>>

    suspend fun clearCart(): Flow<Result<CartItemsResponse>>

    suspend fun fetchCartHistory(): Flow<Result<CartItemsResponse>>

    suspend fun updateItemQuantity(updateItemQuantityRequest: UpdateItemQuantityRequest)
            : Flow<Result<CartItemsResponse>>

}
