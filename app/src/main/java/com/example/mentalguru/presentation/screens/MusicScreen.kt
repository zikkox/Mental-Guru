package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mentalguru.R
import com.example.mentalguru.data.model.Music
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.LoadingComponent
import com.example.mentalguru.presentation.ui.components.TopBar
import com.example.mentalguru.presentation.viewmodels.AuthViewModel

@Composable
fun MusicScreen(navController: NavController) {

    val viewModel: AuthViewModel = viewModel()
    val currentUser = viewModel.currentUser
    val initial = currentUser?.email?.get(0) ?: 'p'

    val musicList = listOf(
        Music(1, "Music 1", "Artist 1", 3, "https://example.com/music1.mp3"),
        Music(2, "Music 2", "Artist 2", 4, "https://example.com/music2.mp3")

    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
            .padding(16.dp)
    ) {

        TopBar(initial, navController)

        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            //Header
            Text(
                text = AnnotatedString("Relax Sounds"),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            //Subtitle
            Text(
                text = AnnotatedString("Sometimes the most productive thing you can do is relax."),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            //Play Now Button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Play Now",
                    color = Color.Black
                )
            }


            //Music List
            musicList?.forEach { music ->
                MusicListItem(music, navController)
            }
        }

        //Show loading indicator
        LoadingComponent(false)
    }
    BottomNavigation(navController)
}

// API Call to get Music List
//    LaunchedEffect(Unit) {
//        viewModel.getMusicList()
//    }}


@Composable
fun MusicListItem(music: Music, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("musicPlayerScreen/${music.id}")
            },
        horizontalArrangement = Arrangement.Start
    ) {
        //Music Image
        Image(
            painter = painterResource(id = R.drawable.bg_timer),
            contentDescription = "Music Image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        //Music Title & Artist
        Column {
            Text(
                text = music.title,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "By: ${music.artist}",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        //Time Indicator (Listening Duration)
        Text(
            text = "${music.duration} Min",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
    }
}