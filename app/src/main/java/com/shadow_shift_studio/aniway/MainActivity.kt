package com.shadow_shift_studio.aniway

import android.R.id.content
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shadow_shift_studio.aniway.data.client.KeyStoreManager
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.view.authentication_screen.Authorization
import com.shadow_shift_studio.aniway.view.bottomnav.Constants
import com.shadow_shift_studio.aniway.view.main_screens.CatalogScreen
import com.shadow_shift_studio.aniway.view.main_screens.MyScreen
import com.shadow_shift_studio.aniway.view.main_screens.ProfileScreen
import com.shadow_shift_studio.aniway.view.main_screens.TopsScreen
import com.shadow_shift_studio.aniway.view.ui.theme.AniWayTheme
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view_model.bottomnav.BottomNavBarViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.CatalogViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.MyViewModel
import com.shadow_shift_studio.aniway.view_model.main_screens.TopsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyStoreManager.initialize(LocalContext.current)
            val viewModelBottom: BottomNavBarViewModel by lazy { BottomNavBarViewModel() }
            var isAuthorization by remember { mutableStateOf(false)}
            AniWayTheme(dynamicColor = false, darkTheme = true) {
                val navController = rememberNavController()
                Surface {
                    var a = KeyStoreManager.getIsLogin()

                    if(a == "1") {
                        AuthorizedUser.username = KeyStoreManager.getUsername().toString()

                        Scaffold(
                            bottomBar = {
                                if (Navbar.getNavbarVisible())
                                    BottomNavigationBar(navController = navController)
                            },
                            content = { padding ->
                                NavHostContainer(
                                    navController = navController,
                                    padding = padding,
                                    viewModelBottom
                                )
                            }
                        )
                    }
                    else
                        Authorization(navController) { isAuthorization = true }
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(content)){view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom=bottom)
            insets
        }
    }
}


@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    viewModelBottom: BottomNavBarViewModel
) {
    val context = LocalContext.current
    val viewModelCatalog = CatalogViewModel(context)
    val scrollStateCatalog = rememberLazyGridState()
    val viewModelTops: TopsViewModel = viewModel()
    val scrollStateTops = rememberLazyListState()
    val viewModelMy = MyViewModel(context)
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
                CatalogScreen(viewModelCatalog, scrollStateCatalog, viewModelBottom)
            }

            composable("tops") {
                viewModelMy.setFirstVisibleItemIndex(scrollStateMy.firstVisibleItemIndex)
                viewModelMy.setFirstVisibleItemScrollOffset((scrollStateMy.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                TopsScreen(viewModelTops, scrollStateTops, viewModelBottom)
            }

            composable("my") {
                viewModelTops.setFirstVisibleItemIndex(scrollStateTops.firstVisibleItemIndex)
                viewModelTops.setFirstVisibleItemScrollOffset((scrollStateTops.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                MyScreen(viewModelMy, scrollStateMy, viewModelBottom)
            }

            composable("profile") {
                viewModelMy.setFirstVisibleItemIndex(scrollStateMy.firstVisibleItemIndex)
                viewModelMy.setFirstVisibleItemScrollOffset((scrollStateMy.firstVisibleItemScrollOffset))
                viewModelTops.setFirstVisibleItemIndex(scrollStateTops.firstVisibleItemIndex)
                viewModelTops.setFirstVisibleItemScrollOffset((scrollStateTops.firstVisibleItemScrollOffset))
                viewModelCatalog.setFirstVisibleItemIndex(scrollStateCatalog.firstVisibleItemIndex)
                viewModelCatalog.setFirstVisibleItemScrollOffset((scrollStateCatalog.firstVisibleItemScrollOffset))
                ProfileScreen(viewModelBottom)
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