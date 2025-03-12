package com.example.woltapp.ui.find_restaurant.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.woltapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val pageTitle: String?,
    val sections: List<Sections>
): Parcelable

@Parcelize
data class Sections(
    val items: List<Items>?,
    val contentType: String?,
    val name: String?,
): Parcelable

@Parcelize
data class Items(
    val venue: Venue?,
    val image: Images?,
): Parcelable

@Parcelize
data class Images(
    val url: String?,
    val blurhash: String?
): Parcelable

@Parcelize
data class Venue(
    val id: String,
    val name: String?,
    val shortDescription: String?,
    val address: String?,
    val country: String?,
): Parcelable
