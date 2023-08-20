import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_outlineVariant

@Composable
fun CommentCard(comment: Comment) {

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
                    .padding(start = 12.dp, top = 6.dp), verticalArrangement = Arrangement.Center
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = comment.username.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                    )
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = comment.text.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                    )
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