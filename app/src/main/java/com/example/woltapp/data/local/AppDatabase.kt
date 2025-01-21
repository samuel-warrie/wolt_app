package com.example.woltapp.data.local

import Restaurants
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Restaurants::class], version = 1)
abstract class AppDatabase : RoomDatabase() {}