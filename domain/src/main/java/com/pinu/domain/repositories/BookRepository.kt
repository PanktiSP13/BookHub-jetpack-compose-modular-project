package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.response.BookListResponseWrapper
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBookList(
        searchString: String? = "",
        genre: String? = null, sortBy: String? = null
    ): Flow<BookListResponseWrapper>
}
