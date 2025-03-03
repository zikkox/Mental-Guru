package com.example.mentalguru.presentation.screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.TopBar

@Composable
fun TimerScreen(navController: NavController) {
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(45 * 60 * 1000L) }  // 45 minutes in milliseconds
    val countDownTimer = remember { mutableStateOf<CountDownTimer?>(null) }

    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            countDownTimer.value = object : CountDownTimer(timeLeft, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = millisUntilFinished
                }

                override fun onFinish() {
                    timerRunning = false
                }
            }.start()
        } else {
            countDownTimer.value?.cancel()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
            .padding(16.dp)
    ) {

        TopBar('p', navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

            Spacer(modifier = Modifier.height(175.dp))

            //Title
            Text(
                text = "Meditation",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(2.dp))

            //Description
            Text(
                text = "Guided by a short introductory course, start trying meditation.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 41.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            Image(
                painter = painterResource(R.drawable.bg_timer),
                contentDescription = "meditating",
                modifier = Modifier
                    .size(280.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(37.dp))

            //Timer
            Text(
                text = String.format("%02d:%02d", timeLeft / 1000 / 60, (timeLeft / 1000) % 60),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(23.dp))

            //Start Now button
            Button(
                onClick = {
                    timerRunning = !timerRunning
                },
                modifier = Modifier
                    .height(61.dp)
                    .width(186.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_green))
            ) {
                Text(
                    text = if (timerRunning) "Pause" else "Start Now",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    BottomNavigation(navController)
}
