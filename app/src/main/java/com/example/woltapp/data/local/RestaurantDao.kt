package com.example.woltapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.woltapp.ui.find_restaurant.AllRestaurant

@Dao
fun interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurant(restaurant: AllRestaurant)
}