package com.example.woltapp.di

import com.example.woltapp.data.local.RestaurantDao
import com.example.woltapp.data.local.RestaurantListRepository
import com.example.woltapp.data.network.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantListRepository(
        restaurantDao: RestaurantDao,
        apiService: APIService
    ): RestaurantListRepository {
        return RestaurantListRepository(restaurantDao, apiService)
    }
}