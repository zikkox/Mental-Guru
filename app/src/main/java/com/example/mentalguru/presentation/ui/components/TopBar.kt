package com.example.mentalguru.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.viewmodels.AuthViewModel

@Composable
fun TopBar(initial: Char, navController: NavController) {

    val viewModel: AuthViewModel = viewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        //Logout
        Image(
            painter = painterResource(R.drawable.ic_logout),
            contentDescription = "Logout",
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterVertically)
                .clickable { viewModel.logout()
                    navController.navigate("welcome")
                }
        )

        //Logo
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
                .scale(2f)
        )

        //Profile picture
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = initial.toString().uppercase(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar('p', navController = rememberNavController())
}