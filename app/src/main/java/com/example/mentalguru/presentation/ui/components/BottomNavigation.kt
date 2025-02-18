package com.example.mentalguru.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mentalguru.R
import com.example.mentalguru.presentation.navigation.Screen

data class NavigationItem(
    val title: String,
    val icon: Int,
    val unselectedSize: Int,
    val selectedSize: Int,
    val route: String
)

val navigationItems = listOf(
    NavigationItem(
        title = "Main",
        icon = R.drawable.ic_main,
        unselectedSize = 60,
        selectedSize = 90,
        route = Screen.Main.route
    ),
    NavigationItem(
        title = "Sounds",
        icon = R.drawable.ic_sounds,
        unselectedSize = 24,
        selectedSize = 36,
        route = Screen.Sounds.route
    ),
    NavigationItem(
        title = "Cart",
        icon = R.drawable.ic_profile,
        unselectedSize = 24,
        selectedSize = 36,
        route = Screen.Profile.route
    )
)

@Composable
fun BottomNavigation(navController: NavController) {
    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry)

    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(currentDestination?.destination?.route) {
        val currentRoute = currentDestination?.destination?.route
        val newIndex = navigationItems.indexOfFirst { it.route == currentRoute }
        if (newIndex != -1) {
            selectedNavigationIndex.intValue = newIndex
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            navigationItems.forEachIndexed { index, item ->
                val isSelected = selectedNavigationIndex.intValue == index

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            selectedNavigationIndex.intValue = index
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        val iconSize = if (isSelected) item.selectedSize else item.unselectedSize
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier
                                .size(iconSize.dp)
                                .animateContentSize()
                                .alpha(if (isSelected) 1f else 0.5f)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    ),
                    label = {}
                )
            }
        }
    }
}


@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigation(navController = rememberNavController())
}
