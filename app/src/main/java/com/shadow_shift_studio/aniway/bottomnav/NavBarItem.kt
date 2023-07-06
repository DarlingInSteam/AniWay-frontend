package com.shadow_shift_studio.aniway.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Segment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.shadow_shift_studio.aniway.CatalogButtonName
import com.shadow_shift_studio.aniway.MyButtonName
import com.shadow_shift_studio.aniway.ProfileButtonName
import com.shadow_shift_studio.aniway.R
import com.shadow_shift_studio.aniway.TopsButtonName

data class NavBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
)
object Constants {
    val BottomNavItems = listOf(
        NavBarItem(
            title = CatalogButtonName,
            icon = Icons.Default.Home,
            route = "catalog"
        ),
        NavBarItem(
            title = TopsButtonName,
            icon = Icons.Filled.LocalFireDepartment,
            route = "tops"
        ),
        NavBarItem(
            title = MyButtonName,
            icon = Icons.Default.Star,
            route = "my"
        ),
        NavBarItem(
            title = ProfileButtonName,
            icon = Icons.Default.AccountCircle,
            route = "profile"
        )
    )
}
