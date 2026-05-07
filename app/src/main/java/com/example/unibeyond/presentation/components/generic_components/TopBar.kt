package com.example.unibeyond.presentation.components.generic_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.unibeyond.R
import com.example.unibeyond.presentation.navigation.Screen
import com.example.unibeyond.ui.theme.UniBeyondGradient

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun UniTopBar(currentRoute: String?, navController: NavController){
    //Select title based on current Route
    val title = when (currentRoute){
        Screen.Discover.route -> stringResource(R.string.top_discover)
        Screen.Map.route -> stringResource(R.string.top_map)
        Screen.MyClubs.route -> stringResource(R.string.top_my_clubs)
        Screen.Profile.route -> stringResource(R.string.top_profile)
        Screen.ClubDetails.route -> stringResource(R.string.club_details)
        Screen.ManageClub.route -> stringResource(R.string.manage_club)
        else -> stringResource(R.string.app_name)
    }

    TopAppBar(
        title = {Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )},
        actions = {
          IconButton(onClick={navController.navigate(Screen.Profile.route)}){
              Surface(
                  modifier = Modifier.size(36.dp),
                  shape = CircleShape,
                  color = Color.White.copy(alpha = 0.2f)
              ){
                  Box(contentAlignment = Alignment.Center){
                      Text(text = "A", color = Color.White, fontWeight = FontWeight.Bold)
                  }
              }
          }
        },
        modifier = Modifier.background(brush = UniBeyondGradient),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),

    )
}