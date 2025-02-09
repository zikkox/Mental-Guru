package com.example.mentalguru.presentation.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(navController: NavController){
    Text(text = "Main Screen")
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(navController = rememberNavController())
}