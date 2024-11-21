package com.pinu.domain.entities.network_service.response

data class BookItemResponse(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val availableQuantity: Int,
    val genre: String,
    val description: String,
    val authorName: String,
    val publishedYear: Int,
    val sellerName: String,
    val numberOfPages: Int,
)

data class BookListResponseWrapper(
    val data: List<BookItemResponse>? = null,
    val error: Throwable? = null
) {
    fun isSuccess(): Boolean {
        return !data.isNullOrEmpty()
    }
}