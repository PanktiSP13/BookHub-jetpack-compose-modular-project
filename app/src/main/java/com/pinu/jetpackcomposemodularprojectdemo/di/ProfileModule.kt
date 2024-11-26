package com.pinu.jetpackcomposemodularprojectdemo.di

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.ProfileAPIs
import com.pinu.data.repositories.ProfileRepositoryImpl
import com.pinu.domain.entities.viewmodels.DashboardViewModel
import com.pinu.domain.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {

    @Provides
    @ViewModelScoped
    fun providesProfileAPIs(network: Network): ProfileAPIs {
        return network.profileAPIService
    }

    @Provides
    @ViewModelScoped
    fun providesProfileRepository(profileAPIs: ProfileAPIs,network: Network): ProfileRepository {
        return ProfileRepositoryImpl(profileAPIs,network)
    }

    @Provides
    @ViewModelScoped
    fun providesDashboardViewModel(profileRepository: ProfileRepository): DashboardViewModel {
        return DashboardViewModel(profileRepository)
    }
}