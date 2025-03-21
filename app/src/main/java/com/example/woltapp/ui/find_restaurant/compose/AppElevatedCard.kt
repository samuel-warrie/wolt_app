package com.example.woltapp.ui.find_restaurant.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.woltapp.R
import com.example.woltapp.ui.theme.AppTypography

@Composable
fun AppElevatedCard(
    gradientColor: Color,
    modifier: Modifier,
    latValue: String?,
    logValue: String?
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
                latValue ?: stringResource(R.string.value),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = AppTypography.bodyMedium
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
                logValue ?: stringResource(R.string.value),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = AppTypography.bodyMedium
            )
        }

    }
}