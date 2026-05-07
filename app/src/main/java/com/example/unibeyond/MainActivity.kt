package com.example.unibeyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unibeyond.presentation.auth.login.LoginScreen
import com.example.unibeyond.presentation.auth.register.RegisterScreen
import com.example.unibeyond.presentation.clubs.manage.ClubManageScreen
import com.example.unibeyond.presentation.clubs.manage.ManageClubViewModel
import com.example.unibeyond.presentation.clubs.view.ClubDetailsScreen
import com.example.unibeyond.presentation.clubs.view.DiscoverScreen
import com.example.unibeyond.presentation.clubs.view.MyClubsScreen
import com.example.unibeyond.presentation.components.generic_components.BottomNavBar
import com.example.unibeyond.presentation.components.generic_components.UniTopBar
import com.example.unibeyond.presentation.navigation.Screen
import com.example.unibeyond.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UniBeyondApp()
        }
    }
}

@Composable
fun UniBeyondApp() {
    //Navigation Controller assignment to local variable
    val navController = rememberNavController()

    //Check what screen we are on
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    //A list that shows which screens use the bottom and top navbar
    val showBottomBar = currentRoute in listOf(
        Screen.Discover.route,
        Screen.Map.route,
        Screen.MyClubs.route,
        Screen.Profile.route,
    )
    val showTopBar = currentRoute in listOf(
        Screen.Discover.route,
        Screen.Map.route,
        Screen.MyClubs.route,
        Screen.Profile.route,
        Screen.ClubDetails.route,
        Screen.ManageClub.route)

    Scaffold(
        topBar = {
            if (showTopBar) {
                UniTopBar(currentRoute = currentRoute, navController = navController)
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->

        //NavHost init
        NavHost(
            navController = navController,
            startDestination = Screen.Discover.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            //First Splash screen
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }

            //Login screen
            //Fake Login button to check the navigation
            composable(Screen.Login.route) {

                LoginScreen(navController = navController)

            }

            composable(Screen.Register.route) {
                RegisterScreen(navController = navController)

            }

            composable(Screen.Map.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "I am on the map screen"
                    )
                }
            }

            composable(Screen.Discover.route) {
                DiscoverScreen(navController = navController)
            }

            composable(Screen.MyClubs.route) {
                MyClubsScreen(navController = navController)
            }

            composable(Screen.Profile.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "I am on the profile screen"
                    )
                }
            }

            composable(Screen.ClubDetails.route) {
                ClubDetailsScreen(navController = navController)


            }

            composable(Screen.ManageClub.route) {
                ClubManageScreen(navController = navController)

            }
        }
    }
}




