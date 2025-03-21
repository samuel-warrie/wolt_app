package com.example.woltapp.data.local

import androidx.room.TypeConverter
import com.example.woltapp.ui.find_restaurant.data.Items
import com.example.woltapp.ui.find_restaurant.data.Sections
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantDataConverter {

    @TypeConverter
    fun fromSections(section: List<Sections>?) : String? {
        return if(section == null) {
            null
        } else {
            Gson().toJson(section)
        }
    }

    @TypeConverter
    fun toSectionList(section: String?) : List<Sections>? {
        return if(section == null) {
            null
        } else {
            val type = object : TypeToken<List<Sections>>() {}.type
            Gson().fromJson<List<Sections>>(section, type)
        }
    }


    @TypeConverter
    fun fromItem(items: List<Items>?) : String? {
        return if(items == null) {
            null
        } else {
            Gson().toJson(items)
        }
    }

    @TypeConverter
    fun toItemList(items: String?) : List<Items>? {
        return if(items == null) {
            null
        } else {
            val type = object : TypeToken<List<Items>>() {}.type
            Gson().fromJson<List<Items>>(items, type)
        }
    }
}