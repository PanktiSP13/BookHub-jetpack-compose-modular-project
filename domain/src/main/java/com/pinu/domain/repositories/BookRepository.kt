package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.response.BookItemResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBookList(searchString: String? = "", genre: String? = null,
                            sortBy: String? = null): Flow<Result<List<BookItemResponse>>>

    suspend fun getBookDetail(bookId : Int) : Flow<Result<BookItemResponse>>
}

