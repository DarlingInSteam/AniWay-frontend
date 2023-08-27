import android.annotation.SuppressLint
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.shadow_shift_studio.aniway.BookmarksAbandoned
import com.shadow_shift_studio.aniway.BookmarksAlreadyRead
import com.shadow_shift_studio.aniway.BookmarksFavorite
import com.shadow_shift_studio.aniway.BookmarksReading
import com.shadow_shift_studio.aniway.BookmarksWillRead
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
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens.BookmarksBottomSheet
import com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens.CommentTextField
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_background
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_bottom_sheet_bottoms
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_onSurfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_outlineVariant
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_high
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_dark_surface_container_higher
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view.ui.theme.surface_container_low
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.MangaPageViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CommentCard(comment: Comment, ) {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = comment.username.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                    )
                    IconButton(
                        onClick = { NeedNormalName.expanded.value = true },
                        modifier = Modifier.height(20.dp)
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
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
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
                        coroutineScope.launch {}
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
