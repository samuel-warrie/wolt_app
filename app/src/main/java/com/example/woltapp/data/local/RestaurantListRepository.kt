package com.example.woltapp.data.local

import com.example.woltapp.data.network.APIService
import com.example.woltapp.data.network.NetworkUiState
import com.example.woltapp.ui.find_restaurant.data.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantListRepository @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val apiService: APIService
) {

    fun fetchRestaurants(lat: Double, log: Double): Flow<NetworkUiState<Restaurant>> = flow {
        emit(NetworkUiState.Loading)
        //get the response call from the API
        val response = apiService.fetchRestaurants(lat, log)

        //limit the response to have just 15 restaurants
        val limitedResponse = response.copy(
            sections = response.sections.map { section ->
                section.copy(
                    items = section.items?.take(15)
                )
            }
        )
        restaurantDao.deleteAllRestaurant()
        restaurantDao.insertRestaurant(limitedResponse)
        emit(NetworkUiState.Success(limitedResponse))
    }.catch { e ->
        emit(NetworkUiState.Error(e))
    }

    fun getRestaurantFromDatabase() : Flow<Restaurant> {
        return restaurantDao.fetchRestaurant()
    }

}