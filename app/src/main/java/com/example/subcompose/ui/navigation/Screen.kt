package com.example.subcompose.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("keranjang")
    object Profile : Screen("profile")
    object DetailMenu : Screen("home/{menuId}") {
        fun createRoute(menuId: Long) = "home/$menuId"
    }
}