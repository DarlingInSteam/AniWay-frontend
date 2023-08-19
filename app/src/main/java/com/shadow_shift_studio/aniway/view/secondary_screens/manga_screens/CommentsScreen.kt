package com.shadow_shift_studio.aniway.view.secondary_screens.manga_screens

import CommentCard
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.shadow_shift_studio.aniway.data.singleton_object.Navbar
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.view.ui.theme.md_theme_light_surfaceVariant
import com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens.CommentsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddComment(navController: NavController, titleId: Long) {
    val context = LocalContext.current
    val viewModel: CommentsViewModel = CommentsViewModel(context)
    viewModel.titleId = titleId
    val commentsState = remember { mutableStateOf<List<Comment>?>(null) }

    val commentsObserver = Observer<List<Comment>> { newComments ->
        commentsState.value = newComments
    }

    LaunchedEffect(viewModel) {
        viewModel.getTitleComments()
    }

    DisposableEffect(viewModel) {
        viewModel.commentsLiveData.observeForever(commentsObserver)

        onDispose {
            viewModel.commentsLiveData.removeObserver(commentsObserver)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack, "", modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                    )
                }
            }
        },
        bottomBar = {
            CommentTextField(viewModel)
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 100.dp),
            ) {
                commentsState.value?.let { it1 -> CommentsFullScreen(viewModel, it1) }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentTextField(viewModel: CommentsViewModel) {
//    var comment by remember { mutableStateOf("") }
    val maxLength = 350
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 23.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
                    .onFocusEvent { event ->
                        if (event.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                        Navbar.setNavbarVisible(!event.isFocused)
                    },
                value = viewModel.commentText.value,
                enabled = true,
                onValueChange = { if (viewModel.commentText.value.length <= maxLength) viewModel.commentText.value = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                )
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.createTitleComment()
                    }
                }
            ) {
                Icon(
                    Icons.Default.Send, ""
                )
            }
        }
        Row {
            Text(
                text = "${viewModel.commentText.value.length} / $maxLength",
                textAlign = TextAlign.Start,
                color = md_theme_light_surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CommentsFullScreen(viewModel: CommentsViewModel, comments: List<Comment>) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.isScrollInProgress) {
        val lastVisibleItemIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@LaunchedEffect
        val totalItemsCount = comments.size ?: return@LaunchedEffect
        if (lastVisibleItemIndex >= totalItemsCount - 1) {
            viewModel.getTitleComments()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(450.dp),
        state = lazyListState,
        content = {
            items(count = comments.size) { index ->
                Row(
                    modifier = Modifier
                        .padding(end = 23.dp, start = 23.dp)
                        .fillMaxWidth()
                ) {
                    CommentCard(comments[index])
                }
                Spacer(modifier = Modifier.height(11.dp))
            }
        }
    )
}



