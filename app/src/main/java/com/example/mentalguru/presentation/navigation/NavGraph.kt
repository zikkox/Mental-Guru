package com.example.mentalguru.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.presentation.screens.SplashScreen
import com.example.mentalguru.presentation.screens.LoginScreen
import com.example.mentalguru.presentation.screens.MainScreen
import com.example.mentalguru.presentation.screens.ProfileScreen
import com.example.mentalguru.presentation.screens.SignUpScreen
import com.example.mentalguru.presentation.screens.MusicScreen
import com.example.mentalguru.presentation.screens.TimerScreen
import com.example.mentalguru.presentation.screens.WelcomeScreen
import com.example.mentalguru.presentation.viewmodels.TimerViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val timerViewModel = TimerViewModel()
    val startScreen = "splash"

    NavHost(navController = navController, startDestination = startScreen) {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("main") { MainScreen(navController) }
        composable("music") { MusicScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("timer") { TimerScreen(navController, timerViewModel) }
    }
}
