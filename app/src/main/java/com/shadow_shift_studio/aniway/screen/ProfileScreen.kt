package com.shadow_shift_studio.aniway.screen

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shadow_shift_studio.aniway.R
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant

@Composable
fun ProfileScreen(){
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(orientation = Orientation.Vertical, state = scrollState),
    ) {
        Wallpaper()
        Spacer(modifier = Modifier.height(12.dp))
        NickAndBadge()
        Spacer(modifier = Modifier.height(20.dp))
        LvLFragment()
    }
}

@Composable
fun LvLFragment() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 23.dp, end = 23.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "LVL 0", color = Color.White, fontSize = 10.sp)
            Text(text = "350/500", color = Color.White, fontSize = 10.sp)
        }
        Row() {
            LinearProgressIndicator(progress = 0.7f, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun NickAndBadge() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "TheNorth", color = Color.White, fontSize = 26.sp)
    }
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Читать F ранга",
            color = md_theme_dark_onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Composable
fun Wallpaper() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(Color.White)
        ) {
            Box() {
                Image(
                    painter = painterResource(R.drawable.wallpaper),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .background(Color.White)
                        .align(Alignment.TopEnd)
                ) {
                }
            }

        }

        Image(
            painter = painterResource(R.drawable.sample_avatar),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
                .align(Alignment.BottomCenter)
        )


    }
}
