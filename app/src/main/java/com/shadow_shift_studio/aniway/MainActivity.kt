package com.shadow_shift_studio.aniway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view_model.CatalogViewModel
import com.shadow_shift_studio.aniway.view_model.MyViewModel
import com.shadow_shift_studio.aniway.view_model.TopsViewModel

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
    padding: PaddingValues,
) {
    val viewModelCatalog: CatalogViewModel = viewModel()
    val scrollStateCatalog = rememberLazyGridState()
    val viewModelTops: TopsViewModel = viewModel()
    val scrollStateTops = rememberLazyListState()
    val viewModelMy: MyViewModel = viewModel()
    val scrollStateMy = rememberLazyGridState()

    NavHost(
        navController = navController,

        startDestination = "catalog",

        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            composable("catalog") {
                viewModelMy.setFirstVisibleItemIndex(scrollStateMy.firstVisibleItemIndex)
                viewModelMy.setFirstVisibleItemScrollOffset((scrollStateMy.firstVisibleItemScrollOffset))
                viewModelTops.setFirstVisibleItemIndex(scrollStateTops.firstVisibleItemIndex)
                viewModelTops.setFirstVisibleItemScrollOffset((scrollStateTops.firstVisibleItemScrollOffset))
                LaunchedEffect(Unit) {
                    scrollStateCatalog.scrollToItem(viewModelCatalog.firstVisibleItemIndex.value)
                }
                CatalogScreen(viewModelCatalog, scrollStateCatalog)
            }

            composable("tops") {
                viewModelMy.setFirstVisibleItemIndex(scrollStateMy.firstVisibleItemIndex)
                viewModelMy.setFirstVisibleItemScrollOffset((scrollStateMy.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                TopsScreen(viewModelTops, scrollStateTops)
            }

            composable("my") {
                viewModelTops.setFirstVisibleItemIndex(scrollStateTops.firstVisibleItemIndex)
                viewModelTops.setFirstVisibleItemScrollOffset((scrollStateTops.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                MyScreen(viewModelMy, scrollStateMy)
            }

            composable("profile") {
                viewModelMy.setFirstVisibleItemIndex(scrollStateMy.firstVisibleItemIndex)
                viewModelMy.setFirstVisibleItemScrollOffset((scrollStateMy.firstVisibleItemScrollOffset))
                viewModelTops.setFirstVisibleItemIndex(scrollStateTops.firstVisibleItemIndex)
                viewModelTops.setFirstVisibleItemScrollOffset((scrollStateTops.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                ProfileScreen()
            }
        })

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(containerColor = md_theme_dark_background) {

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