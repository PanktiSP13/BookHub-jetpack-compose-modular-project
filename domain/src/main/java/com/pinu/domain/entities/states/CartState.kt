package com.pinu.domain.entities.states
import com.pinu.domain.entities.network_service.response.CartItemsResponse

data class CartState(
    var cartItemResponse: CartItemsResponse? = null,
    val isLoading : Boolean = false,
    val isLoadingForPayment : Boolean = false,
    val itemMovedToCart : Boolean = false,
    val reloadBookDetail : Boolean = false)