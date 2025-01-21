package com.example.woltapp.ui.find_restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.woltapp.R
import com.example.woltapp.ui.theme.AppTypography

@Composable
fun RestaurantListScreen(
    paddingValues: PaddingValues,
    modifier: Modifier
) {

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
        val data = dummyData()
        LazyColumn(
            modifier = modifier.padding(10.dp)
        ) {
            val itemList: List<Items> = data.sections.flatMap {
                section ->
                section.items.orEmpty()
            }
            items(itemList.size) { index ->
                ItemCard(itemList[index], modifier.padding(bottom = 8.dp))
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
            .height(100.dp)
            .background(colorResource(R.color.card_background_color))
    ) {
        // Restaurant image
        Image(
            painter = rememberAsyncImagePainter(
                model = item.image.url,
                placeholder = painterResource(R.drawable.image_placeholder),
                error = painterResource(R.drawable.icon_error)
            ),
            contentDescription = null,
            modifier = modifier
                .width(70.dp)
                .height(70.dp)
                .padding(4.dp).align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop // ContentScale.Crop makes sure the image fills the entire box
        )

        Column(
            modifier = modifier
                .padding(start = 12.dp)
        ) {
            // Restaurant details
            Text(
                text = item.venue.name.toString(),
                style = AppTypography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(id = R.color.text_color)
            )

            Spacer(modifier = modifier.height(5.dp))

            Text(
                text = item.venue.shortDescription.toString(),
                style = AppTypography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = modifier.height(10.dp))

            // Address and Country
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = item.venue.address.toString(),
                    style = AppTypography.bodyMedium,
                    color = Color.Black
                )

                Text(
                    text = item.venue.country.toString(),
                    style = AppTypography.bodyMedium,
                    color = Color.Black
                )

            }
        }

        IconButton(onClick = {

        }, modifier = modifier.padding(start = 8.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.favorite_border_icon),
                contentDescription = stringResource(R.string.like_button),
                modifier = modifier
                    .width(50.dp)
                    .height(50.dp),
                tint = Color.Green
            )
        }
    }

}


