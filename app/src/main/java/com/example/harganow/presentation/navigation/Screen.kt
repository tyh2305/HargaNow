package com.example.harganow.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Order : Screen("order")
    object Announcement : Screen("announcement")
    object Profile : Screen("profile")
}
