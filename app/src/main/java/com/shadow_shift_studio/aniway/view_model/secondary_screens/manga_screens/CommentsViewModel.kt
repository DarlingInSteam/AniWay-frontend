package com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.CommentsRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.CommentsUseCase
import com.shadow_shift_studio.aniway.model.entity.Comment
import kotlinx.coroutines.launch

class CommentsViewModel(private val context: Context) : ViewModel() {
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val commentText = mutableStateOf("")

    var titleId: Long = 0
    var page = 0

    private val createComment: CommentsUseCase =
        CommentsUseCase(CommentsRequest())
    suspend fun getTitleComments() {
        viewModelScope.launch {
            val comments = createComment.getTitleComments(context, titleId, page)

            val currentComments = commentsLiveData.value ?: emptyList()
            val updatedComments = currentComments.toMutableList()
            updatedComments.addAll(comments)

            commentsLiveData.value = updatedComments

            page += 1
        }.join()
    }

    suspend fun createTitleComment() {
        viewModelScope.launch {
            val buf = createComment.createComment(context, titleId, chapterId = 0, commentText.value)
            Log.e("Comment value", commentText.value)
        }.join()
    }

    suspend fun deleteTitleComment(id: Long){
        viewModelScope.launch {
            val buf = createComment.deleteComment(context, AuthorizedUser.username, id)
            Log.e("", buf)
        }.join()
    }

    suspend fun updateTitleComment(id: Long, text:String) {
        viewModelScope.launch {
            val buf = createComment.updateComment(context, AuthorizedUser.username, id, text)
            Log.e("Comment value", commentText.value)
        }.join()
    }
}