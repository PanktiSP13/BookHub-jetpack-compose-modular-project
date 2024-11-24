package com.pinu.jetpackcomposemodularprojectdemo.di

import android.content.Context
import com.pinu.data.db.BookHubDatabase
import com.pinu.data.network.Network
import com.pinu.jetpackcomposemodularprojectdemo.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesNetwork(): Network {
        return Network.init()
    }


    @Singleton
    @Provides
    fun providesBookHubDatabase(@ApplicationContext context: Context): BookHubDatabase {
        return BookHubDatabase.getDatabase(context)
    }

}