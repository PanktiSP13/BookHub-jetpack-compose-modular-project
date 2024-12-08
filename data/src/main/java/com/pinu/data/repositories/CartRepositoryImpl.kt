package com.pinu.data.repositories

import com.pinu.data.network.network_apis.CartAPIs
import com.pinu.domain.entities.network_service.request.AddToCartRequest
import com.pinu.domain.entities.network_service.request.UpdateItemQuantityRequest
import com.pinu.domain.entities.network_service.response.CartItemsResponse
import com.pinu.domain.repositories.CartRepository
import com.pinu.domain.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(private val cartAPIs: CartAPIs) : CartRepository {

    override suspend fun addToCart(cartRequest: AddToCartRequest): Flow<Result<CartItemsResponse>> {
        return NetworkUtils.safeApiCall { cartAPIs.addToCart(bookId = cartRequest.bookId, quantity = cartRequest.qty) }
    }

    override suspend fun removeItemFromCart(bookId: Int): Flow<Result<CartItemsResponse>> {
        return NetworkUtils.safeApiCall { cartAPIs.removeFromCart(bookId) }
    }

    override suspend fun clearCart(): Flow<Result<CartItemsResponse>> {
        return NetworkUtils.safeApiCall { cartAPIs.clearCart() }
    }

    override suspend fun fetchCartHistory(): Flow<Result<CartItemsResponse>> {
        return NetworkUtils.safeApiCall { cartAPIs.fetchCartItems() }
    }

    override suspend fun updateItemQuantity(updateItemQuantityRequest: UpdateItemQuantityRequest)
    : Flow<Result<CartItemsResponse>> {
        return NetworkUtils.safeApiCall {
            cartAPIs.updateQuantity(
                bookId = updateItemQuantityRequest.bookId,
                quantity = updateItemQuantityRequest.quantity
            )
        }
    }

}