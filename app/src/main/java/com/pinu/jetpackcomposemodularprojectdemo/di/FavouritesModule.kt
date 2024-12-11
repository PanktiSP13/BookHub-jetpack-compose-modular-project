package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.FavouritesAPIs
import com.pinu.data.repositories.FavouriteRepositoryImpl
import com.pinu.domain.entities.viewmodels.FavouriteViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.domain.repositories.FavouriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavouritesModule {


    @Provides
    @Singleton
    fun providesWishlistAPIs(network: Network): FavouritesAPIs {
        return network.favouritesAPIService
    }

    @Provides
    @Singleton
    fun providesFavouriteRepository(favouriteAPI: FavouritesAPIs): FavouriteRepository {
        return FavouriteRepositoryImpl(favouriteAPI)
    }

    @Provides
    @Singleton
    fun providesFavouriteViewModel(
        sharedViewModel: SharedViewModel,
        favouriteRepository: FavouriteRepository,
    ): FavouriteViewModel {
        return FavouriteViewModel(sharedViewModel, favouriteRepository)
    }
}