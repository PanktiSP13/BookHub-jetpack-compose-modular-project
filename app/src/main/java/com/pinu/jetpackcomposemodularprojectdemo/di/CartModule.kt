package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.CartAPIs
import com.pinu.data.repositories.CartRepositoryImpl
import com.pinu.domain.entities.viewmodels.CartViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.domain.repositories.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {


    @Provides
    @Singleton
    fun providesCartAPIs(network: Network): CartAPIs {
        return network.cartAPIService
    }

    @Provides
    @Singleton
    fun providesCartRepository(cartAPIs: CartAPIs): CartRepository{
        return CartRepositoryImpl(cartAPIs)
    }

    @Provides
    @Singleton
    fun providesCartViewModel(sharedViewModel: SharedViewModel,cartRepository: CartRepository):CartViewModel{
        return CartViewModel(sharedViewModel,cartRepository)
    }
}