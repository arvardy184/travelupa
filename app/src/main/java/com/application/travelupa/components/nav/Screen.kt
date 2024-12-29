package com.application.travelupa.components.nav

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object RekomendasiTempat : Screen("rekomendasiTempat")
    data object Register : Screen("register")
    data object  Greeting : Screen("greeting")
}