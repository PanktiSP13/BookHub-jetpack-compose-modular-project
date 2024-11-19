package com.pinu.domain.entities.network_service.response

class CartItemsResponse(
    val items: List<CartItem>,
    val totalItems: Int,
    val totalPrice: Double
) {

    data class CartItem(
        val bookId: Int,
        val title: String,
        val author: String,
        val price: Double,
        val quantity: Int
    )
}


