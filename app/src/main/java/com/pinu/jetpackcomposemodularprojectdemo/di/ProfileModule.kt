package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.ProfileAPIs
import com.pinu.data.repositories.ProfileRepositoryImpl
import com.pinu.domain.entities.viewmodels.DashboardViewModel
import com.pinu.domain.entities.viewmodels.SharedViewModel
import com.pinu.domain.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun providesProfileAPIs(network: Network): ProfileAPIs {
        return network.profileAPIService
    }

    @Provides
    @Singleton
    fun providesProfileRepository(profileAPIs: ProfileAPIs,network: Network): ProfileRepository {
        return ProfileRepositoryImpl(profileAPIs,network)
    }

    @Provides
    @Singleton
    fun providesDashboardViewModel(sharedViewModel: SharedViewModel,profileRepository: ProfileRepository): DashboardViewModel {
        return DashboardViewModel(sharedViewModel,profileRepository)
    }
}