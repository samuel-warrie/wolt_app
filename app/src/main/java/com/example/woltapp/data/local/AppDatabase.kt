package com.example.woltapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.woltapp.ui.find_restaurant.data.Restaurant

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
@TypeConverters(RestaurantDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao() : RestaurantDao
}