package com.pinu.domain.entities.states

import com.pinu.domain.entities.network_service.response.CartItemsResponse

data class CartState(
    var cartItemList : CartItemsResponse? = null,
    val isLoading : Boolean = false,
)