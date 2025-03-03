package com.example.mentalguru.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mentalguru.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.presentation.navigation.Screen
import com.example.mentalguru.presentation.ui.components.BottomNavigation
import com.example.mentalguru.presentation.ui.components.TopBar
import com.example.mentalguru.presentation.viewmodels.AuthViewModel

@Composable
fun MainScreen(navController: NavController) {

    val viewModel: AuthViewModel = viewModel()
    val currentUser = viewModel.currentUser

    val moodList = listOf(
        Mood("Calm", R.drawable.ic_calm),
        Mood("Relax", R.drawable.ic_relax),
        Mood("Focus", R.drawable.ic_focus),
        Mood("Anxious", R.drawable.ic_anxious)
    )

    val meditationList = listOf(
        MeditationItem(
            "Timer Meditation",
            "Time waits for no one! Use it while you can!",
            R.drawable.ic_lesson_three,
            "",
            false
        ),
        MeditationItem(
            "Meditation 101",
            "Techniques, Benefits, and a Beginner’s How-To",
            R.drawable.ic_lesson_one,
            "https://www.gaiam.com/blogs/discover/meditation-101-techniques-benefits-and-a-beginner-s-how-to",
            true
        ), MeditationItem(
            "Cardio Meditation",
            "Basics of Yoga for Beginners or Experienced Professionals",
            R.drawable.ic_lesson_two,
            "https://www.ozofsalt.com/cardio-meditation-helping-become-mindful/",
            true
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_green))
            .padding(24.dp)
    ) {

        //Top bar
        TopBar(currentUser?.email?.get(0) ?: 'p', navController)

        Spacer(modifier = Modifier.height(31.dp))

        Column(modifier = Modifier.padding(horizontal = 10.dp)) {

            //Greeting Text
            Text(
                text = "Welcome back, ${currentUser?.email?.substringBefore("@") ?: "Guest"}!",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.W400,
                letterSpacing = 0.5.sp
            )
            Text(
                text = "How are you feeling today?",
                color = Color.Gray,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 5.dp)
            )


            //Mood selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 27.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                moodList.forEach { mood ->
                    MoodButton(mood)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            //Meditation cards
            LazyColumn(
                modifier = Modifier
                    .height(425.dp)
                    .clip(RoundedCornerShape(20.dp)),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                items(meditationList) { item ->
                    MeditationCard(item, navController)
                }
            }

        }
    }

    BottomNavigation(navController)
}

@Composable
fun MoodButton(mood: Mood) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(mood.icon),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.dark_green)),
            contentDescription = mood.name,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(50f))
                .background(colorResource(id = R.color.opaque_white))
                .padding(12.dp)
                .clickable { /* TODO: Handle mood click */ })
        Text(
            text = mood.name, color = Color.White, fontSize = 12.sp
        )
    }
}

@Composable
fun MeditationCard(item: MeditationItem, navController: NavController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.yellow_white)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .height(110.dp)
                    .width(170.dp)
                    .align(Alignment.CenterEnd)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Column {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.5.sp,
                        fontSize = 25.sp,
                        color = colorResource(R.color.dark_green)
                    )
                    Text(
                        text = item.description,
                        fontSize = 15.sp,
                        color = colorResource(R.color.dark_green),
                        modifier = Modifier.width(170.dp),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Button(
                        onClick = {
                            if (item.redirectsToWeb) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.webURL))
                                context.startActivity(intent)
                            } else {
                                navController.navigate(Screen.Timer.route)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.dark_green)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = if (item.redirectsToWeb) "watch now" else "start your timer",
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = if (item.redirectsToWeb) painterResource(R.drawable.ic_watch) else painterResource(
                                R.drawable.ic_clock
                            ),
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier
                                .size(13.dp)
                        )

                    }
                }
            }
        }
    }
}


data class Mood(val name: String, val icon: Int)
data class MeditationItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val webURL: String,
    val redirectsToWeb: Boolean
)


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}