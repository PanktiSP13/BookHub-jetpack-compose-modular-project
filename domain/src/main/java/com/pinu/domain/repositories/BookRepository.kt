package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.response.BookResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBookList(
        searchString: String? = "",
        genre: String? = null,
        sortBy: String? = null
    ): Flow<Result<BookResponse>>

    suspend fun getBookDetail(bookId: Int): Flow<Result<BookResponse.BookItemResponse>>
}

