package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.BookResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksAPIs {

    /* -------------- Books Service --------------------- */

//    @GET("/book-list")
    @GET("https://mocki.io/v1/4d450e34-0939-477e-89c9-e38739edce98")
    suspend fun getBookList(
        @Query("search") search: String? = null, // Search books by name or keyword
        @Query("genre") genre: String? = null, // Filter by genre
        @Query("sort") sort: String? = null // Sort by popularity, rating, etc.
    ): BookResponse

//    @POST("/book/{id}")
    @GET("https://mocki.io/v1/e5c0586c-93c7-43b3-bc89-1e78f570007c")
    suspend fun getBookDetails(/*@Path("id") bookId: Int*/): BookResponse.BookItemResponse

}