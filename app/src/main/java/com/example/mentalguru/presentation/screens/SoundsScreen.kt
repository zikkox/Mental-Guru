package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.ui.components.BottomNavigation

//sounds screen ui goes here
@Composable
fun SoundsScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
    ) {

    }
    BottomNavigation(navController)
}