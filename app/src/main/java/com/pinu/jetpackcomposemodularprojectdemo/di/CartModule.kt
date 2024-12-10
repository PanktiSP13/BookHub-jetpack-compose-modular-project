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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CartModule {


    @Provides
    @ViewModelScoped
    fun providesCartAPIs(network: Network): CartAPIs {
        return network.cartAPIService
    }

    @Provides
    @ViewModelScoped
    fun providesCartRepository(cartAPIs: CartAPIs): CartRepository{
        return CartRepositoryImpl(cartAPIs)
    }

    @Provides
    @ViewModelScoped
    fun providesCartViewModel(sharedViewModel: SharedViewModel,cartRepository: CartRepository):CartViewModel{
        return CartViewModel(sharedViewModel,cartRepository)
    }
}