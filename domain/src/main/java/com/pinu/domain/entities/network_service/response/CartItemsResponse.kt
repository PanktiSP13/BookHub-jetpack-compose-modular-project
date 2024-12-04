package com.pinu.domain.entities.network_service.response

import com.google.gson.annotations.SerializedName

class CartItemsResponse(
    val success: Boolean = false,
    val message: String = "",
    val status: Int? = null,
    val data: CartItemsData? = null
) {

    data class CartItemsData(
        val items: List<CartItem> = emptyList(),
        @SerializedName("total_items")val totalItems: Int = 0,
        @SerializedName("total_price")val totalPrice: Double? = null
    ) {
        data class CartItem(
            @SerializedName("book_id")val bookId: Int? = null,
            val title: String = "",
            val author: String = "",
            @SerializedName("img_url")val imgUrl :String = "",
            val price: Double? = null,
            val quantity: Int = 1
        )
    }
}


