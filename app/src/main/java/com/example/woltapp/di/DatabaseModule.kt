package com.example.woltapp.di

import android.app.Application
import androidx.room.Room
import com.example.woltapp.data.local.AppDatabase
import com.example.woltapp.data.local.RestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java,
            "app.db").build()
    }

    //provide all DAOs too
    @Provides
    @Singleton
    fun provideRestaurantDao(database: AppDatabase) : RestaurantDao {
        return database.restaurantDao()
    }
}