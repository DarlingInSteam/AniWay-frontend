import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandCircleDown
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowCircleDown
import androidx.compose.material.icons.outlined.ArrowCircleUp
import androidx.compose.material.icons.outlined.ExpandCircleDown
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_outlineVariant

@Composable
fun CommentCard(comment: Comment) {

    val expanded = remember { mutableStateOf(false) }


    androidx.compose.material3.Card(
        modifier = Modifier
            .clickable { },
        colors = CardColors(
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background,
            md_theme_dark_background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(md_theme_dark_outlineVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 12.dp, top = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageComment(comment)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp, end = 12.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = comment.username.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    IconButton(
                        onClick = { expanded.value = true },
                        modifier = Modifier.height(20.dp)
                    ) {
                        Icon(
                            Icons.Default.MoreHoriz, "",
                        )
                        DropdownMenu( expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            offset = DpOffset((-40).dp, (-40).dp)){
                            Text("Редактировать", fontSize=18.sp, modifier = Modifier.padding(10.dp).clickable(onClick={}))
                            Text("Удалить", fontSize=18.sp, modifier = Modifier.padding(10.dp).clickable(onClick={}))
                            Text("Пожаловаться", fontSize=18.sp, modifier = Modifier.padding(10.dp).clickable(onClick={}))
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = comment.titleName.toString(),
                        color = md_theme_dark_onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = comment.text.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                }
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = {/*TODO*/ },
                        modifier = Modifier.height(35.dp).width(35.dp)) {
                        Icon(Icons.Outlined.ExpandLess, "",
                            tint = md_theme_dark_onSurfaceVariant)
                    }
                    Text(
                        text = "24",
                        color = md_theme_dark_onSurfaceVariant,
                        fontSize =15.sp
                    )
                    IconButton(onClick = {/*TODO*/ },
                        modifier = Modifier.height(35.dp).width(35.dp)) {
                        Icon(Icons.Outlined.ExpandMore, "",
                            tint = md_theme_dark_onSurfaceVariant)
                    }
                }

            }
        }
    }
}

@Composable
fun ImageComment(comment: Comment) {
    Column {
        AsyncImage(
            model = comment.avatarUrl.toString(),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    }
}