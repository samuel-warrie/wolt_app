package com.example.woltapp.ui.find_restaurant.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FindRestaurantNavHost(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues // Add this parameter
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "restaurantList") {
        composable("restaurantList") {
            RestaurantListScreen(
                paddingValues = paddingValues, // Pass paddingValues to RestaurantListScreen
                onAboutClick = { navController.navigate("about") },
                modifier = modifier
            )
        }
        composable("about") {
            AboutScreen(onBackClick = { navController.popBackStack() })
        }
    }
}