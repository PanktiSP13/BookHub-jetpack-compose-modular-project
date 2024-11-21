package com.pinu.data.repositories

import com.pinu.data.network.network_apis.BooksAPIs
import com.pinu.domain.entities.network_service.response.BookListResponseWrapper
import com.pinu.domain.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry


class BookRepositoryImpl(private val booksAPIs: BooksAPIs) : BookRepository {

    override suspend fun getBookList(
        searchString: String?,
        genre: String?,
        sortBy: String?
    ): Flow<BookListResponseWrapper> = flow {
        emit(
            BookListResponseWrapper(
                data = booksAPIs.getBookList(
                    search = searchString ?: "",
                    genre = genre,
                    sort = sortBy
                ),
            )
        )
    }.flowOn(Dispatchers.IO).retry(2).catch { error ->
        emit(BookListResponseWrapper(error = error))
    }

}