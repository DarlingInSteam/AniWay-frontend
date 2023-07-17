package com.shadow_shift_studio.aniway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.bottomnav.Constants
import com.shadow_shift_studio.aniway.screens.CatalogScreen
import com.shadow_shift_studio.aniway.screens.MyScreen
import com.shadow_shift_studio.aniway.screens.ProfileScreen
import com.shadow_shift_studio.aniway.screens.TopsScreen
import com.shadow_shift_studio.aniway.ui.theme.AniWayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AniWayTheme(dynamicColor = false, darkTheme = true) {
                val navController = rememberNavController()

                Surface {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        },
                        content = { padding ->
                            NavHostContainer(navController = navController, padding = padding)
                        }
                    )
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

    NavigationBar {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

                NavigationBarItem(
                    selected = currentRoute == navItem.route,

                    onClick = {
                        navController.navigate(navItem.route)
                    },

                    icon = {
                        Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                    },

                    label = {
                        Text(text = navItem.title)
                    },

                    alwaysShowLabel = true
                )

        }
    }
}