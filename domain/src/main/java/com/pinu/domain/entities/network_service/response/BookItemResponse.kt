package com.pinu.domain.entities.network_service.response

import com.google.gson.annotations.SerializedName


data class BookResponse(
    val success: Boolean = false,
    val message: String = "",
    val status: Int? = null,
    val data: List<BookItemResponse>? = null
) {
    data class BookItemResponse(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val price: Double,
        val availableQuantity: Int,
        val genre: String,
        val description: String,
        val authorName: String,
        @SerializedName("book_published_data") val bookPublishedDate: String,
        val publishedYear: Int,
        val sellerName: String,
        val numberOfPages: Int,
        val isInCart: Boolean = false,
        val isFavourite: Boolean = false
    )

}

