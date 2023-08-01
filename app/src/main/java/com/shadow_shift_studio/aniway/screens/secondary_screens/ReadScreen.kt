package com.shadow_shift_studio.aniway.screens.secondary_screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_background

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReadScreen(navController: NavController)
{
    val navControllerReadPage = rememberNavController()

    val images: Array<String> = arrayOf(
        "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
        "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
        "https://img-cdn.trendymanga.com/covers/upscaled_ab5e34f9-a69d-4d3a-8c45-d480742f9cc5.jpg",
        )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            BottomBar(images)
        },
        content =
        {
            ImageList(images)
        }
    )
}

@Composable
fun TopBar(navController: NavController)
{
    var volumeNumber: Int = 1
    var chapterNumber: Int = 77
    var dash: String = "-"
    var volumeChapterText: String = "$volumeNumber$dash$chapterNumber"

    Row(modifier = Modifier.fillMaxWidth()
        .background(md_theme_dark_background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
        Text(
            text = volumeChapterText,
            fontSize = 22.sp,

        )
        IconButton(onClick = {/*TODO*/}) {
            Icon(
                Icons.Default.Menu, "", modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }
    }
}

@Composable
fun ImageList(imagesResource: Array<String>)
{
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()))
    {
        for(i in imagesResource) {
            AsyncImage(
                contentScale = ContentScale.FillWidth,
                model = i,
                contentDescription = "",
            )
        }
    }
}

@Composable
fun BottomBar(imagesResource: Array<String>)
{
    var likeCount: Int = 22
    var imageIndex : Int = 1
    var imageCount: Int = imagesResource.size
    var slash: String = "/"
    var indexCountText: String  = "$imageIndex$slash$imageCount"
    Row(modifier = Modifier.fillMaxWidth()
        .background(md_theme_dark_background)
        .padding(start = 23.dp, end = 23.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column(){
            Row()
            {
                Icon(
                    Icons.Default.FavoriteBorder, ""
                )
                Text(
                    text = likeCount.toString()
                )
                Spacer(modifier = Modifier.width(11.dp))
                Icon(
                    Icons.Default.PhotoLibrary, ""
                )
                Text(
                    text = indexCountText
                )
            }
        }
        Column() {
            Row()
            {
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(Icons.Default.ArrowBackIos, "")
                }
                IconButton(onClick = { /*TODO*/ })
                {
                    Icon(Icons.Default.ArrowForwardIos, "")
                }
            }
        }
    }
}
