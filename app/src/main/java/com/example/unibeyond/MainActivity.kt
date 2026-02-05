package com.example.unibeyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unibeyond.presentation.components.BottomNavBar
import com.example.unibeyond.presentation.components.UniTopBar
import com.example.unibeyond.presentation.navigation.Screen
import com.example.unibeyond.presentation.splash.SplashScreen
import com.example.unibeyond.ui.theme.UniBeyondTheme
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

    //A list that shows which screens use the bottom navbar
    val showBottomBar = currentRoute in listOf(
        Screen.Discover.route,
        Screen.Map.route,
        Screen.MyClubs.route,
        Screen.Profile.route
    )

    Scaffold(
        topBar = {
          if (showBottomBar){
              UniTopBar(currentRoute = currentRoute)
          }
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
            ){ innerPadding ->

        //NavHost init
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ){
            //First Splash screen
            composable(Screen.Splash.route){
                SplashScreen(navController = navController)
            }

            //Login screen
            //Fake Login button to check the navigation
            composable(Screen.Login.route){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Button(onClick = {
                        navController.navigate(Screen.Discover.route){
                            popUpTo(Screen.Login.route){
                                inclusive = true
                            }
                        }
                    })
                    {
                        Text(text="Login")
                    }
                }
            }

            composable(Screen.Register.route){

            }

            composable(Screen.Map.route){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(
                        text="I am on the map screen"
                    )
                }
            }

            composable(Screen.Discover.route){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(
                        text="I am on the discover screen"
                    )
                }
            }

            composable(Screen.MyClubs.route){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(
                        text="I am on the my clubs screen"
                    )
                }
            }

            composable(Screen.Profile.route){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(
                        text="I am on the profile screen"
                    )
                }
            }





        }
    }
}




