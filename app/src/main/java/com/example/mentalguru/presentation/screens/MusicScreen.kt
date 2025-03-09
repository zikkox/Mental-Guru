package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mentalguru.R
import com.example.mentalguru.data.model.Music
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.LoadingComponent
import com.example.mentalguru.presentation.ui.components.TopBar
import com.example.mentalguru.presentation.viewmodels.AuthViewModel
import com.example.mentalguru.presentation.viewmodels.MusicViewModel

@Composable
fun MusicScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()
    val currentUser = viewModel.currentUser
    val initial = currentUser?.email?.get(0) ?: 'p'

    val musicViewModel: MusicViewModel = viewModel()
    val musicList by musicViewModel.musicList.collectAsState()
    val isLoading by musicViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        musicViewModel.loadMusic()
    }

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
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(20.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 37.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Relax Sounds",
                        color = Color.White,
                        fontSize = 27.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Sometimes the most productive thing you can do is relax.",
                        color = Color.White,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(20.dp))

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
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            //LazyColumn for music list
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 30.dp)
                    .padding(bottom = 112.dp)
                    .clip(RoundedCornerShape(20.dp)),
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                items(musicList) { music ->
                    MusicListItem(music, navController)
                }
            }
        }

        LoadingComponent(isLoading)
    }
    BottomNavigation(navController)
}


@Composable
fun MusicListItem(music: Music, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("sound/${music.id}")
            },
        horizontalArrangement = Arrangement.Start
    ) {
        //Cover image
        AsyncImage(
            //model = music.cover,
            model = R.drawable.ic_cover,
            contentDescription = "Cover Image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        //Music title and artist
        Column(modifier = Modifier.width(170.dp)) {
            Text(
                text = music.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "By: ${music.artist}",
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        //Time indicator (listening duration)
        Text(
            text = "${music.duration} Min",
            color = Color.White,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}