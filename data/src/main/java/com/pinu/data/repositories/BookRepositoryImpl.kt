package com.pinu.data.repositories

import com.pinu.data.network.network_apis.BooksAPIs
import com.pinu.domain.entities.network_service.response.BookResponse
import com.pinu.domain.repositories.BookRepository
import com.pinu.domain.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(private val booksAPIs: BooksAPIs) : BookRepository {

    override suspend fun getBookList(
        searchString: String?, genre: String?, sortBy: String?
    ): Flow<Result<BookResponse>> {

        return NetworkUtils.safeApiCall(retryCount = 2) {
            booksAPIs.getBookList(search = searchString , genre = genre, sort = sortBy)
        }
    }

    override suspend fun getBookDetail(bookId: Int): Flow<Result<BookResponse.BookItemResponse>> {
        return NetworkUtils.safeApiCall { booksAPIs.getBookDetails(bookId) }
    }


}