package com.shadow_shift_studio.aniway.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.shadow_shift_studio.aniway.CatalogButtonName
import com.shadow_shift_studio.aniway.MyButtonName
import com.shadow_shift_studio.aniway.ProfileButtonName
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
            icon = Local_fire_department,
            route = "tops"
        ),
        NavBarItem(
            title = MyButtonName,
            icon = Icons.Default.star_border,
            route = "my"
        ),
        NavBarItem(
            title = ProfileButtonName,
            icon = Icons.Default.account_circle_filled_24px,
            route = "profile"
        )
    )
}
