package com.example.harganow.presentation.navigation

sealed class Screen(val route: String, val label: String, val icon: Int) {
    object Home : Screen("main", "Home", 0)
    object Order : Screen("order", "Order", 1)
    object Announcement : Screen("announcement", "Announcement", 2)
    object Profile : Screen("user_settings", "Profile", 3)
}
