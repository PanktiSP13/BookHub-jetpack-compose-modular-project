package com.pinu.domain.entities.network_service.response

class CartItemsResponse(
    val success: Boolean = false,
    val message: String = "",
    val status: Int? = null,
    val data: CartItemsData? = null
) {

    data class CartItemsData(
        val items: List<CartItem> = emptyList(),
        val totalItems: Int = 0,
        val totalPrice: Double? = null
    ) {
        data class CartItem(
            val bookId: Int? = null,
            val title: String = "",
            val author: String = "",
            val imgUrl :String = "",
            val price: Double? = null,
            val quantity: Int = 1
        )
    }
}


