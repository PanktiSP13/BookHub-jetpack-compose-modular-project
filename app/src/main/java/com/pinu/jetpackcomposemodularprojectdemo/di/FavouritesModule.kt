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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FavouritesModule {


    @Provides
    @ViewModelScoped
    fun providesWishlistAPIs(network: Network): FavouritesAPIs {
        return network.favouritesAPIService
    }

    @Provides
    @ViewModelScoped
    fun providesFavouriteRepository(favouriteAPI: FavouritesAPIs): FavouriteRepository {
        return FavouriteRepositoryImpl(favouriteAPI)
    }

    @Provides
    @ViewModelScoped
    fun providesFavouriteViewModel(
        sharedViewModel: SharedViewModel,
        favouriteRepository: FavouriteRepository,
    ): FavouriteViewModel {
        return FavouriteViewModel(sharedViewModel, favouriteRepository)
    }
}