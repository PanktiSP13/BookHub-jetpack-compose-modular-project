package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.BooksAPIs
import com.pinu.data.repositories.BookRepositoryImpl
import com.pinu.domain.entities.viewmodels.BooksViewModel
import com.pinu.domain.repositories.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BookModule {

    @Provides
    @ViewModelScoped
    fun providesBooksApi(network: Network): BooksAPIs {
        return network.booksAPIService
    }

    @Provides
    @ViewModelScoped
    fun providesBookRepository(booksAPIs: BooksAPIs): BookRepository {
        return BookRepositoryImpl(booksAPIs)
    }

    @Provides
    @ViewModelScoped
    fun providesBooksViewModel(bookRepository: BookRepository): BooksViewModel {
        return BooksViewModel(bookRepository)
    }
}