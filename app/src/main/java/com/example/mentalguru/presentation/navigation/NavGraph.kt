package com.example.mentalguru.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.presentation.Screen
import com.example.mentalguru.presentation.screens.SplashScreen
import com.example.mentalguru.presentation.screens.LoginScreen
import com.example.mentalguru.presentation.screens.MainScreen
import com.example.mentalguru.presentation.screens.SignUpScreen
import com.example.mentalguru.presentation.screens.WelcomeScreen
import com.example.mentalguru.viewmodels.AuthViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val startScreen = "splash"

    NavHost(navController = navController, startDestination = startScreen) {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("main") { MainScreen(navController) }
    }
}
