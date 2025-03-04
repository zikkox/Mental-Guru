package com.example.mentalguru.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.viewmodels.AuthViewModel

@Composable
fun TopBar(initial: Char, navController: NavController) {

    val viewModel: AuthViewModel = viewModel()

    // Get current destination
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //Determine the icon based on the current screen
        val iconRes =
            if (currentDestination == "timer") R.drawable.ic_back_arrow else R.drawable.ic_logout
        val description = if (currentDestination == "timer") "Back" else "Logout"

        //Icon (Back or Logout)
        Image(painter = painterResource(iconRes),
            contentDescription = description,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    if (currentDestination != "home") {
                        viewModel.logout()
                        navController.navigate("main")
                    }
                })


        Spacer(modifier = Modifier.weight(1f))

        //Logo
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(60.dp)
                .scale(2f)
        )

        Spacer(modifier = Modifier.weight(1f))

        if (currentDestination == "profile") {
            //Edit button
            Text(
                text = "edit",
                color = Color.White,
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {

                    }
            )
        } else {
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
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar('p', navController = rememberNavController())
}