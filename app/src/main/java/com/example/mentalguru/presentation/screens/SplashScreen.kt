package com.example.mentalguru.presentation.screens

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.navigation.Screen
import com.example.mentalguru.presentation.viewmodels.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var isLogoVisible by remember { mutableStateOf(false) }

    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val isLoggedIn = remember { mutableStateOf(viewModel.authRepository.isUserLoggedIn()) }

    LaunchedEffect(Unit) {
        delay(300)
        isLogoVisible = true
        delay(1500)

        //Navigate based on login state
        if (isLoggedIn.value) {
            navController.navigate(Screen.Main.route)
        } else {
            navController.navigate(Screen.Welcome.route)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_welcome),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = isLogoVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}