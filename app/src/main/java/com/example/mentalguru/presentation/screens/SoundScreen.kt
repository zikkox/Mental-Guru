package com.example.mentalguru.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mentalguru.R
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.LoadingComponent
import com.example.mentalguru.presentation.ui.components.TopBar
import com.example.mentalguru.presentation.viewmodels.AuthViewModel
import com.example.mentalguru.presentation.viewmodels.MusicViewModel

@Composable
fun SoundScreen(musicId: String, navController: NavController) {

    val viewModel: AuthViewModel = viewModel()
    val currentUser = viewModel.currentUser
    val initial = currentUser?.email?.get(0) ?: 'p'

    val musicViewModel: MusicViewModel = viewModel()
    val music = musicViewModel.music
    val isLoading by musicViewModel.isLoading.collectAsState()

    LaunchedEffect(musicId) {
        musicViewModel.getMusicById(musicId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(initial, navController)

        Spacer(modifier = Modifier.height(55.dp))

        AsyncImage(
            //model = music.value?.cover,
            model = R.drawable.ic_cover,
            contentDescription = "Cover Image",
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = music.value?.title ?: "Title",
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = music.value?.artist ?: "Artist",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(130.dp))

        MusicControls()

    }
    LoadingComponent(isLoading)

    BottomNavigation(navController)
}

@Composable
fun MusicControls(
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        // Previous Button
//        IconButton(onClick = onPreviousClick) {
//            Icon(
//                imageVector = Icons.Default.SkipPrevious,
//                contentDescription = "Previous",
//                tint = Color.Gray,
//                modifier = Modifier.size(40.dp)
//            )
//        }

        Spacer(modifier = Modifier.width(16.dp))

        // Play/Pause Button
        IconButton(
            onClick = { /* Handle play/pause button click */ },
            Modifier.size(75.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = "Play/Pause",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

//        // Next Button
//        IconButton(onClick = onNextClick) {
//            Icon(
//                imageVector = Icons.Default.SkipNext,
//                contentDescription = "Next",
//                tint = Color.Gray,
//                modifier = Modifier.size(40.dp)
//            )
//        }
    }
}
