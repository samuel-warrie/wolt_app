package com.example.woltapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.woltapp.R
import com.example.woltapp.ui.find_restaurant.compose.FindRestaurantNavHost
import com.example.woltapp.ui.theme.AppTheme
import com.example.woltapp.ui.theme.AppTypography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = colorResource(R.color.white),
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(R.color.white),
                            ),
                            navigationIcon = {
                                IconButton(onClick = {
                                    // TODO: Implement menu action if needed
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = stringResource(R.string.menu_icon)
                                    )
                                }
                            },
                            title = {
                                Text(
                                    stringResource(R.string.home),
                                    textAlign = TextAlign.Center,
                                    style = AppTypography.titleMedium
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    // TODO: Implement settings action if needed
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = stringResource(R.string.setting_description)
                                    )
                                }
                            },
                        )
                    }
                ) { paddingValues ->
                    FindRestaurantNavHost(
                        paddingValues = paddingValues // Pass paddingValues to NavHost
                    )
                }
            }
        }
    }
}