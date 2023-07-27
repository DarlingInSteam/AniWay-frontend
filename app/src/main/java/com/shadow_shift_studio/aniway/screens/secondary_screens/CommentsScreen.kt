package com.shadow_shift_studio.aniway.screens.secondary_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.screens.Comments
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.ui.theme.md_theme_dark_surface_container_higher

@Composable
fun AddComment(navController: NavController) {
    var comment by remember { mutableStateOf("") }
    val maxLength = 350
    Column() {
        Row(modifier = Modifier.height(300.dp))
        {
            Comments()
        }

        Spacer(modifier = Modifier.height(11.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(md_theme_dark_surface_container_higher),
                contentAlignment = Alignment.CenterStart
            ) {

                BasicTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(top = 20.dp, start = 23.dp, end = 23.dp),
                    value = comment,
                    enabled = true,
                    onValueChange = { if (it.length <= maxLength) comment = it },
                    maxLines = 4,
                    textStyle = TextStyle(
                        color = md_theme_dark_onSurfaceVariant,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    ),
                )
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.Send, "", modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 10.dp)
                    )
                }
                Divider(thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd), color = md_theme_dark_onSurfaceVariant)
            }
    Text(
        text = "${comment.length} / $maxLength",
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
    )
            }
        }
    }
}

