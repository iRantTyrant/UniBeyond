package com.example.unibeyond.presentation.navigation

sealed class Screen (val route : String){

    //Splash screen
    object Splash : Screen("splash_screen")

    //Authentication Screens (no bottom nav bar)
    //Login screen
    object Login : Screen("login_screen")
    //Signup screen
    object Register : Screen("register_screen")


    //Main screens (with a bottom nav bar)
    //Event details
    object Events : Screen("events_screen")
    //Main discover club screen
    object Discover : Screen("discover_screen")
    //My clubs screen
    object MyClubs : Screen("my_clubs_screen")
    //Profile screen
    object Profile : Screen("profile_screen")
    //Screen that shows upcoming events
    object Map : Screen("map_screen")
    //Club Details screen
    object ClubDetails : Screen("club_details_screen/{clubId}")

}


