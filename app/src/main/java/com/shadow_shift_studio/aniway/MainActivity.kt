package com.shadow_shift_studio.aniway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shadow_shift_studio.aniway.bottomnav.Constants
import com.shadow_shift_studio.aniway.bottomnav.NavBarItem
import com.shadow_shift_studio.aniway.screen.CatalogScreen
import com.shadow_shift_studio.aniway.screen.MyScreen
import com.shadow_shift_studio.aniway.screen.ProfileScreen
import com.shadow_shift_studio.aniway.screen.TopsScreen
import com.shadow_shift_studio.aniway.ui.theme.AniWayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AniWayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,

        startDestination = "catalog",

        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            composable("catalog") {
                CatalogScreen()
            }

            composable("tops") {
                TopsScreen()
            }

            composable("my") {
                MyScreen()
            }

            composable("profile") {
                ProfileScreen()
            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(

        backgroundColor = Color(0xFF201a18)
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

            NavBarItem(

                selected = currentRoute == navItem.route,

                onClick = {
                    navController.navigate(navItem.route)
                },

                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },

                title = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}