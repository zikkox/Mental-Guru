package com.example.mentalguru.presentation.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.mentalguru.R

@Composable
fun SnackbarHostComponent(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = colorResource(R.color.light_green),
            contentColor = colorResource(R.color.white)
        )
    }
}