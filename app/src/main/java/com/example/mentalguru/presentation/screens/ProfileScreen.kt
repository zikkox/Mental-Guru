package com.example.mentalguru.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.TopBar
import com.example.mentalguru.presentation.viewmodels.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController) {


    val viewModel: AuthViewModel = viewModel()
    val currentUser = viewModel.currentUser
    val initial = currentUser?.email?.get(0) ?: 'p'

//    val userName by viewModel.userName.collectAsState()
//    val userLocation by viewModel.userLocation.collectAsState()
//    val userDob by viewModel.userDob.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
            .padding(16.dp)
    ) {

        TopBar(initial, navController)

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(145.dp))

            //Profile picture
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Text(
                    text = currentUser?.email?.get(0).toString(),
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //Username
            Text(
                text = AnnotatedString(currentUser?.email?.substringBefore("@") ?: "Guest"),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            //Location
            Text(
                text = AnnotatedString("India"),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            //DoB
            Text(
                text = AnnotatedString("1992"),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(19.dp))

            //Mottos
            Image(
                painter = painterResource(id = R.drawable.bg_motto_one),
                contentDescription = "Motto 1",
                modifier = Modifier
                    .width(308.dp)
                    .height(142.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(19.dp))

            Image(
                painter = painterResource(id = R.drawable.bg_motto_two),
                contentDescription = "Motto 2",
                modifier = Modifier
                    .width(308.dp)
                    .height(142.dp),
                contentScale = ContentScale.Crop
            )

        }
    }

    BottomNavigation(navController)
}
