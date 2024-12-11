package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.BooksAPIs
import com.pinu.data.repositories.BookRepositoryImpl
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.domain.repositories.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookModule {

    @Provides
    @Singleton
    fun providesBooksApi(network: Network): BooksAPIs {
        return network.booksAPIService
    }

    @Provides
    @Singleton
    fun providesBookRepository(booksAPIs: BooksAPIs): BookRepository {
        return BookRepositoryImpl(booksAPIs)
    }

    @Provides
    @Singleton
    fun providesBooksViewModel(
        sharedViewModel: SharedViewModel,
        bookRepository: BookRepository,
    ): BooksViewModel {
        return BooksViewModel(sharedViewModel, bookRepository)
    }
}