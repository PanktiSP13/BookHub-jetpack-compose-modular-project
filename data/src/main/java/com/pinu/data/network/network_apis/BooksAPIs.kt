package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.BookItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksAPIs {

    /* -------------- Books Service --------------------- */

    @GET("/book-list")
    suspend fun getBookList(
        @Query("search") search: String = "", // Search books by name or keyword
        @Query("genre") genre: String? = null, // Filter by genre
        @Query("sort") sort: String? = null // Sort by popularity, rating, etc.
    ): List<BookItemResponse>

    @GET("/book/{id}")
    suspend fun getBookDetails(@Path("id") bookId: String): BookItemResponse

}