package com.example.unibeyond.presentation.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.unibeyond.R
import com.example.unibeyond.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun UniTopBar(currentRoute: String?){
    //Select title based on current Route
    val title = when (currentRoute){
        Screen.Discover.route -> stringResource(R.string.top_discover)
        Screen.Map.route -> stringResource(R.string.top_map)
        Screen.MyClubs.route -> stringResource(R.string.top_my_clubs)
        Screen.Profile.route -> stringResource(R.string.top_profile)
        else -> stringResource(R.string.app_name)
    }

    CenterAlignedTopAppBar(
        title = {Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}