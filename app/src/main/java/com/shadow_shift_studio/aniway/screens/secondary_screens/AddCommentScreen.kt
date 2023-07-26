package com.shadow_shift_studio.aniway.screens.secondary_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shadow_shift_studio.aniway.screens.Comments

@Preview
@Composable
fun AddComment() {
    var comment by remember { mutableStateOf("Оставьте комментарий") }
    Column() {
        Row()
        {
            Comments()
        }

        Spacer(modifier = Modifier.height(11.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {

            TextField(
                value = comment,
                onValueChange = { newText -> comment = newText },
                maxLines = 5,
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
            )
            IconButton(
                onClick = { },
            ) {
                Icon(Icons.Default.ArrowRight, contentDescription = "")
            }

        }
    }
}

