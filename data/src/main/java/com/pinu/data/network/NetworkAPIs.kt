package com.pinu.data.network
import com.pinu.domain.entities.BookItemDataModel
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkAPIs {


    @GET("/book-list")
    suspend fun getBookList(@Query("search") search:String = "") : List<BookItemDataModel>

}