package com.pinu.domain.usecases

import com.pinu.domain.entities.network_service.response.BookItemResponse
import com.pinu.domain.repositories.BookRepository
import kotlinx.coroutines.flow.flow


class BookUseCase(val repository: BookRepository) {

    suspend fun apiCallGetBookList(
        searchString: String? = "",
        genre: String? = null,
        sortBy: String? = null,
        onFailure: (String) -> Unit
    ) = flow<Result<List<BookItemResponse>>> {

        repository.getBookList(searchString = searchString, genre = genre, sortBy = sortBy)
            .collect {
                //modify data here to make it UI ready
                emit(
                    if (it.isSuccess()) Result.success(it.data ?: emptyList())
                    else Result.failure(it.error ?: Throwable(message = "Something went wrong!!"))
                )

            }
    }

}