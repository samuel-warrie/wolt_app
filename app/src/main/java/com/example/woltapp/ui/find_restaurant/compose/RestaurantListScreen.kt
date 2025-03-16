package com.example.woltapp.ui.find_restaurant.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.woltapp.R
import com.example.woltapp.data.network.NetworkUiState
import com.example.woltapp.ui.find_restaurant.FindRestaurantViewModel
import com.example.woltapp.ui.find_restaurant.data.Items
import com.example.woltapp.ui.find_restaurant.data.Restaurant
import com.example.woltapp.ui.theme.AppTypography

@Composable
fun RestaurantListScreen(
    paddingValues: PaddingValues,
    viewModel: FindRestaurantViewModel = hiltViewModel(),
    onAboutClick: () -> Unit, // Add this parameter to match NavGraph.kt
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentCoordinates by viewModel.coordinates.collectAsState()

    val rest by viewModel.restaurantsFromDb.collectAsState(
        initial = Restaurant(0, null, null, emptyList())
    )

    val context = LocalContext.current
    var restaurants: Restaurant? = null

    LaunchedEffect(Unit) {
        viewModel.fetchRestaurant()
    }

    when (uiState) {
        is NetworkUiState.Loading -> {
            CircularLoader()
        }
        is NetworkUiState.Success -> {
            restaurants = (uiState as NetworkUiState.Success<Restaurant>).data
        }
        is NetworkUiState.Error -> {
            Toast.makeText(context, stringResource(R.string.error_text), Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues),
    ) {
        // Coordinate value cards
        AppElevatedCard(
            colorResource(R.color.pink),
            modifier,
            currentCoordinates?.lat?.toString(),
            currentCoordinates?.log?.toString()
        )
        // About App Button (placed below coordinate card)
        Button(
            onClick = onAboutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.about_app_button))
        }

        // Nearby restaurant text
        Text(
            stringResource(R.string.restaurant_list_text),
            color = colorResource(R.color.text_color),
            modifier = modifier.padding(16.dp),
            style = AppTypography.titleMedium
        )

        // Restaurant list
        LazyColumn(
            modifier = modifier.padding(10.dp)
        ) {
            if (restaurants != null) {
                val itemList: List<Items> = restaurants.sections.flatMap { section ->
                    section.items.orEmpty()
                }.filter { item ->
                    item.venue != null && item.venue.name?.isNotBlank() == true
                }

                items(itemList.size) { index ->
                    ItemCard(itemList[index], modifier.padding(bottom = 8.dp))
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: Items, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Restaurant image
        Image(
            painter = rememberAsyncImagePainter(
                model = item.image?.url,
                placeholder = painterResource(R.drawable.image_placeholder),
                error = painterResource(R.drawable.icon_error)
            ),
            contentDescription = null,
            modifier = modifier
                .width(75.dp)
                .height(70.dp)
                .padding(4.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )

        // Restaurant information
        Column(
            modifier = modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.venue?.name ?: stringResource(R.string.name),
                style = AppTypography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = modifier.height(5.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = item.venue?.address ?: stringResource(R.string.address),
                    style = AppTypography.bodyMedium,
                    color = Color.Black
                )

                VerticalDivider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .height(2.dp)
                )

                Text(
                    text = item.venue?.country ?: stringResource(R.string.country),
                    style = AppTypography.bodyMedium,
                    color = Color.Black
                )
            }

            Spacer(modifier = modifier.height(5.dp))

            HorizontalDivider(
                color = Color.Black,
                thickness = 0.3.dp
            )
        }

        // Favorite icon
        Box(
            modifier = modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.favorite_border_icon),
                contentDescription = stringResource(id = R.string.like_button),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    }
}