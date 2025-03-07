package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                .fillMaxSize()
                .padding(top = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.bg_music),
                    contentDescription = "Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                        .clip(shape = RoundedCornerShape(20.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 37.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    //Header
                    Text(
                        text = "Relax Sounds",
                        color = Color.White,
                        fontSize = 27.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    //Description
                    Text(
                        text = "Sometimes the most productive thing you can do is relax.",
                        color = Color.White,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    //Play now button
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "play now",
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(R.drawable.ic_watch),
                            contentDescription = "Play",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(13.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            //Music list
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