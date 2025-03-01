package com.example.mentalguru.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object Welcome : Screen("welcome")
    data object Main : Screen("main")
    data object Sounds : Screen("sounds")
    data object Profile : Screen("profile")
    data object Timer : Screen("timer")
}