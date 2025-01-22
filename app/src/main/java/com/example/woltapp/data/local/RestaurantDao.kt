package com.example.woltapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.woltapp.ui.find_restaurant.data.Restaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Query("SELECT * from restaurants")
    fun fetchRestaurant(): Flow<Restaurant>

    @Query("DELETE FROM restaurants")
    suspend fun deleteAllRestaurant()
}