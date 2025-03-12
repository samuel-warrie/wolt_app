package com.example.woltapp.ui.find_restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woltapp.data.local.RestaurantListRepository
import com.example.woltapp.data.network.NetworkUiState
import com.example.woltapp.ui.find_restaurant.data.Coordinates
import com.example.woltapp.ui.find_restaurant.data.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindRestaurantViewModel @Inject constructor(
    private val repository: RestaurantListRepository
): ViewModel() {
    val navigateToAbout = MutableStateFlow(false)
    private val _uiState = MutableStateFlow<
            NetworkUiState<Restaurant>>(NetworkUiState.Success(Restaurant(0, "", "", emptyList())))
    val uiState: StateFlow<NetworkUiState<Restaurant>> = _uiState

    private val _coordinates = MutableStateFlow<Coordinates?>(null)
    val coordinates: StateFlow<Coordinates?> get() = _coordinates


    val restaurantsFromDb = repository.getRestaurantFromDatabase()

    //coordinates list
    private val allCoordinates = listOf(
        Coordinates(60.169418, 24.931618),
        Coordinates(60.169818, 24.932906),
        Coordinates(60.170005, 24.935105),
        Coordinates(60.169108, 24.936210),
        Coordinates(60.168355, 24.934869),
        Coordinates(60.167560, 24.932562),
        Coordinates(60.168254, 24.931532),
        Coordinates(60.169012, 24.930341),
        Coordinates(60.170085, 24.929569)
    )

    // Job to manage coroutine
    private var fetchJob: Job? = null

    fun fetchRestaurant() {
        fetchJob = viewModelScope.launch {
            //if there is an ongoing job, cancel it first before starting a new one
            fetchJob?.cancel()

            var coordinateIndex = 0

            while(true) {
                val currentCoordinate = allCoordinates[coordinateIndex]

                //print coordinate values to screen
                println("lat is: " + currentCoordinate.lat + "and log is: " + currentCoordinate.log )

                //make the API call based on the current coordinate
                repository.fetchRestaurants(currentCoordinate.lat, currentCoordinate.log).collect {
                        result ->
                    //update the ui with the result
                    _uiState.value = result
                }

                //update coordinates value to the current values
                _coordinates.value = currentCoordinate

                //a 10secs delay
                delay(10000)

                //increment index;
                //for step 2, new index will be 1, 1 % 9 =
                coordinateIndex = (coordinateIndex + 1) % allCoordinates.size

            }
        }
    }

    private fun cancelJob() {
        fetchJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob() //when the viewmodel is cleared, ensure that the job is cleared
    }
}