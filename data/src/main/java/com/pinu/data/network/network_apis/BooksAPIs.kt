package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.BookResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BooksAPIs {

    /* -------------- Books Service --------------------- */

    @GET("api/books")
    suspend fun getBookList(
        @Query("search") search: String? = null, // Search books by name or keyword
        @Query("genre") genre: String? = null, // Filter by genre
        @Query("sort") sort: String? = null // Sort by popularity, rating, etc.
    ): BookResponse

    @POST("api/books")
    suspend fun getBookDetails(@Query("id") bookId: Int): BookResponse.BookItemResponse

}