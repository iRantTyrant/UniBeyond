package com.example.unibeyond.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.unibeyond.R
import com.example.unibeyond.presentation.navigation.Screen




data class BottomNavItem(
    val name : String,
    val route : String,
    val icon : ImageVector,
)


@Composable
fun BottomNavBar(
    navController : NavController
){
    //The 4 tappable buttons
    val items = listOf(
        BottomNavItem("Discover",Screen.Discover.route, Icons.Outlined.Group),
        BottomNavItem("Map", Screen.Map.route, Icons.Outlined.Map),
        BottomNavItem("My Clubs", Screen.MyClubs.route, Icons.Outlined.FavoriteBorder),
        BottomNavItem("Profile", Screen.Profile.route, Icons.Outlined.Person)
    )

    //Keep track the top most item in the viewing stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //The NavBar UI
    NavigationBar{
        //For every item in the list
        items.forEach {item ->
            //Create a navigation bar item
            NavigationBarItem(
                //Selected item is the one that is on the top of the stack
                selected = currentRoute == item.route,
                //When clicked navigate to that screen
                onClick = { navController.navigate(item.route) {popUpTo(navController.graph.startDestinationId){
                    //Save the state of the previous screen
                    saveState = true
                }
                    //Avoid multiple copies of the same destination
                    launchSingleTop = true
                    //Restore state when reselecting a previously selected item
                    restoreState = true
                } },

                icon = {Icon(imageVector = item.icon, contentDescription = item.name)},
                label = {Text(text = item.name)},
                colors = NavigationBarItemDefaults.colors(
                    //The colour of the icon and its label when tapped
                    selectedIconColor = colorResource(R.color.purple_300),
                    selectedTextColor = colorResource(R.color.purple_300),

                    //Make the background Transparent
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}