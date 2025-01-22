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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    modifier: Modifier
) {

    val uiState = viewModel.uiState.collectAsState()
    val rest = viewModel.restaurantsFromDb.collectAsState(
        initial = Restaurant(0, null, null, emptyList())
    )

    val context = LocalContext.current
    var restaurants: Restaurant? = null

    LaunchedEffect(Unit) {
        viewModel.fetchRestaurant()
    }

    when(uiState.value) {
        is NetworkUiState.Loading -> {
           //show loading view
            CircularLoader()
        }
        is NetworkUiState.Success -> {
            restaurants = (uiState.value as NetworkUiState.Success<Restaurant>).data
        }
        is NetworkUiState.Error -> {
            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(paddingValues)
    ) {
        //initial top cards
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            modifier = modifier.padding(16.dp)
        ) {
            AppElevatedCard(
                colorResource(R.color.pink),
                modifier
            )

            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(1.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = modifier
                    .width(200.dp)
                    .height(80.dp)
                    .padding(6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.teal_blue),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .padding(start = 6.dp, top = 6.dp)
                ) {
                    Text(
                        stringResource(R.string.time),
                        color = Color.White,
                        style = AppTypography.bodyMedium
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        stringResource(R.string.time_value),
                        color = Color.White,
                        style = AppTypography.bodySmall
                    )
                }
            }

        }

        //nearby restaurant text
        Text(
            stringResource(R.string.restaurant_list_text),
            color = colorResource(R.color.text_color),
            modifier = modifier.padding(16.dp),
            style = AppTypography.titleMedium

        )


        //Restaurant list
        LazyColumn(
            modifier = modifier.padding(10.dp)
        ) {
            if(restaurants != null) {
                val itemList: List<Items> = restaurants.sections.flatMap { section ->
                    section.items.orEmpty() // Avoid null list
                }.filter { item ->
                    item.venue != null && item.venue.name?.isNotBlank() == true // Ensure venue is not null and name is not blank
                }

                items(itemList.size) { index ->
                    ItemCard(itemList[index], modifier.padding(bottom = 8.dp))
                }
            }


        }
    }
}


@Composable
fun AppElevatedCard(
    gradientColor: Color,
    modifier: Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(1.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .width(200.dp)
            .height(80.dp)
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = gradientColor,
            contentColor = colorResource(R.color.white)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(start = 6.dp, top = 6.dp)
        ) {
            Text(
                stringResource(R.string.latitude),
                color = Color.White,
                style = AppTypography.bodyMedium
            )
            Text(
                stringResource(R.string.value),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = AppTypography.bodySmall
            )
        }

        Spacer(modifier = modifier.height(5.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(start = 6.dp, top = 6.dp)
        ) {
            Text(
                stringResource(R.string.longitude),
                color = Color.White,
                style = AppTypography.bodyMedium
            )
            Text(
                stringResource(R.string.value),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = AppTypography.bodySmall
            )
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
                .padding(4.dp).align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop // ContentScale.Crop makes sure the image fills the entire box
        )

        //restaurant information
        Column(
            modifier = modifier.weight(1f).padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Restaurant details
            Text(
                text = item.venue?.name ?: "Name",
                style = AppTypography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = modifier.height(5.dp))

            // Address and Country
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {

                Text(
                    text = item.venue?.address ?: "Address",
                    style = AppTypography.bodyMedium,
                    color = Color.Black
                )

                VerticalDivider(thickness = 1.dp, color = Color.Black,
                    modifier = modifier.padding(start = 4.dp, end = 4.dp).height(2.dp)
                )

                Text(
                    text = item.venue?.country ?: "Country",
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

        //favorite icon
        Box(
            modifier = modifier.width(40.dp).height(40.dp)
                //.background(Color.Black)
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


