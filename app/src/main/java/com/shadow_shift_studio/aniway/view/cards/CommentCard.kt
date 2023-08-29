import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.CallForBullying
import com.shadow_shift_studio.aniway.Complain
import com.shadow_shift_studio.aniway.CopyComment
import com.shadow_shift_studio.aniway.DeleteComment
import com.shadow_shift_studio.aniway.EditComment
import com.shadow_shift_studio.aniway.FakeAccount
import com.shadow_shift_studio.aniway.Offense
import com.shadow_shift_studio.aniway.Spam
import com.shadow_shift_studio.aniway.Spoiler
import com.shadow_shift_studio.aniway.TextIsCopy
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.data.singleton_object.NeedNormalName
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_higher
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.surface_container_low
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CommentCard(comment: Comment) {
    val context = LocalContext.current
    val viewModel: CommentsViewModel = CommentsViewModel(context)
    val coroutineScope = rememberCoroutineScope()

    Card(
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
//                .background(md_theme_dark_outlineVariant)
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
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 6.dp, end = 12.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row {
                        Text(
                            text = comment.username.toString(),
                            color = Color.White,
                            fontSize = 15.sp,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = formatCommentTimestamp(createdAt = comment.createdAt!!),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = { NeedNormalName.expanded.value = true },
                        modifier = Modifier
                            .height(20.dp)
                            .align(Alignment.CenterVertically),
                        colors = IconButtonColors(containerColor = md_theme_dark_background, contentColor = Color.White, disabledContainerColor = Color.White, disabledContentColor = Color.White)
                    ) {
                        Icon(
                            Icons.Default.MoreHoriz, "",
                        )
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
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Используем SpaceBetween для размещения элементов слева и справа
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Ответить", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.clickable {  })

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Пространство между элементами
                    ) {
                        IconButton(
                            onClick = {/*TODO*/ },
                            modifier = Modifier
                                .height(35.dp)
                                .width(35.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ExpandLess, "",
                                tint = md_theme_dark_onSurfaceVariant
                            )
                        }
                        Text(
                            text = "24",
                            color = md_theme_dark_onSurfaceVariant,
                            fontSize = 15.sp
                        )
                        IconButton(
                            onClick = {/*TODO*/ },
                            modifier = Modifier
                                .height(35.dp)
                                .width(35.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ExpandMore, "",
                                tint = md_theme_dark_onSurfaceVariant
                            )
                        }
                    }
                }


            }
        }
        if (NeedNormalName.expanded.value) {
            CommentMenu (comment, viewModel)
        }
        if(NeedNormalName.needCopyText.value) {
            CopyText(comment = comment)
            coroutineScope.launch {
                delay(4000)
                NeedNormalName.snackbarVisible.value = false
            }
        }
    }
    AnimatedVisibility(
        visible = NeedNormalName.reportsBottomSheetVisible.value,
        enter = slideInVertically(initialOffsetY = { height -> height }, animationSpec = tween()),
        exit = slideOutVertically(targetOffsetY = { height -> height }, animationSpec = tween()),
        content = {
            ReportsBottomSheet(onClose = {
                NeedNormalName.reportsBottomSheetVisible.value = false
            })
        }
    )
}

@Composable
fun formatCommentTimestamp(createdAt: Date): String {
    val currentTime = Calendar.getInstance().time
    val commentTime = Calendar.getInstance()
    commentTime.time = createdAt

    val now = currentTime.time
    val differenceInMillis = now - createdAt.time

    val seconds = differenceInMillis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60

    val dayMonthFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
    val monthName = monthFormat.format(createdAt)

    val monthNames = mapOf(
        "Jan" to "янв.",
        "Feb" to "фев.",
        "Mar" to "мар.",
        "Apr" to "апр.",
        "May" to "мая",
        "Jun" to "июн.",
        "Jul" to "июл.",
        "Aug" to "авг.",
        "Sep" to "сен.",
        "Oct" to "окт.",
        "Nov" to "ноя.",
        "Dec" to "дек."
    )

    val formattedMonthName = monthNames[monthName] ?: monthName

    return when {
        seconds < 60 -> "$seconds сек назад"
        minutes < 60 -> "$minutes мин назад"
        createdAt.isSameDay(currentTime) -> "сегодня в ${timeFormat.format(createdAt)}"
        createdAt.isYesterday(currentTime) -> "вчера в ${timeFormat.format(createdAt)}"
        else -> "${dayMonthFormat.format(createdAt)} в ${timeFormat.format(createdAt)}"
    }.replace(monthName, formattedMonthName)
}

fun Date.isSameDay(otherDate: Date): Boolean {
    val cal1 = Calendar.getInstance()
    cal1.time = this
    val cal2 = Calendar.getInstance()
    cal2.time = otherDate
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

fun Date.isYesterday(otherDate: Date): Boolean {
    val cal1 = Calendar.getInstance()
    cal1.time = this
    cal1.add(Calendar.DAY_OF_YEAR, 1)
    val cal2 = Calendar.getInstance()
    cal2.time = otherDate
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
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

@Composable
fun CommentMenu(comment: Comment, viewModel: CommentsViewModel){
    val isVisible: Boolean = isUserCommentAuthor(comment)
    val coroutineScope = rememberCoroutineScope()

    Dialog(onDismissRequest = {
        NeedNormalName.expanded.value = false
    }) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(md_theme_dark_surface_container_higher)
            .fillMaxWidth()
        )
        {
            if(isVisible){
            Text(
                EditComment,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp, top = 10.dp, bottom = 10.dp)
                    .clickable(onClick = {
                        coroutineScope.launch {
                            NeedNormalName.expanded.value = false
                        }
                    }),
                color = md_theme_dark_onSurfaceVariant
            )
            Text(
                DeleteComment,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp, top = 10.dp, bottom = 10.dp)
                    .clickable(onClick = {
                        coroutineScope.launch {
                            NeedNormalName.expanded.value = false
                            viewModel.deleteTitleComment(comment.id)
                        }
                        /*onChangeExpand()*/
                    }),
                color = md_theme_dark_onSurfaceVariant
            )}
            Text(
                CopyComment,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp, top = 10.dp, bottom = 10.dp)
                    .clickable(onClick = {
                        NeedNormalName.needCopyText.value = true
                        NeedNormalName.expanded.value = false
                    }),
                color = md_theme_dark_onSurfaceVariant
            )
            Text(
                Complain,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp, end = 23.dp, top = 10.dp, bottom = 10.dp)
                    .clickable(onClick = {
                        NeedNormalName.reportsBottomSheetVisible.value = true
                        NeedNormalName.expanded.value = false
                    }),
                color = md_theme_dark_onSurfaceVariant
            )
        }
    }

}

private fun isUserCommentAuthor(comment: Comment): Boolean{
    var res: Boolean = false
    if(comment.username == AuthorizedUser.username)
        res = true
    return res
}

@Composable
private fun CopyText(comment: Comment){
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    clipboardManager.setText(AnnotatedString((comment.text.toString())))

    if(NeedNormalName.snackbarVisible.value) {
        Snackbar() {
            Text(text = TextIsCopy)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsBottomSheet(onClose: () -> Unit) {

    val scaffoldState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        sheetState = scaffoldState,
        modifier = Modifier.height(400.dp),
        containerColor = surface_container_low
    ) {
        ButtonsForReports(onClose = { onClose() })
    }
}

@Composable
fun ButtonsForReports(onClose: () -> Unit) {
    Column {
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = Spam)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = FakeAccount)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = Offense)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = CallForBullying)
        }
        Button(
            shape = RoundedCornerShape(7.dp),
            onClick = {
                onClose()
            },
            modifier = Modifier
                .padding(start = 23.dp, end = 23.dp)
                .fillMaxWidth(),
            colors = ButtonColors(
                md_theme_dark_bottom_sheet_bottoms,
                md_theme_light_surfaceVariant,
                Color.White,
                Color.White
            )
        ) {
            Text(text = Spoiler)
        }
    }
}
