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
        @SerializedName("image_url") val imageUrl: String,
        val price: Double,
        @SerializedName("qty") val availableQuantity: Int,
        val genre: String,
        val description: String,
        @SerializedName("author") val authorName: String,
        @SerializedName("book_published_date") val bookPublishedDate: String,
        @SerializedName("published_year") val publishedYear: Int,
        @SerializedName("seller_name") val sellerName: String,
        @SerializedName("no_of_pages") val numberOfPages: Int,
        @SerializedName("is_in_cart") val isInCart: Boolean = false,
        @SerializedName("is_favourite") val isFavourite: Boolean = false
    )
}

