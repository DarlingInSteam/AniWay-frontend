package com.shadow_shift_studio.aniway.view_model.secondary_screens.manga_screens

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.aniway.data.api_request.CommentsRequest
import com.shadow_shift_studio.aniway.data.api_request.TitleRequest
import com.shadow_shift_studio.aniway.data.singleton_object.AuthorizedUser
import com.shadow_shift_studio.aniway.domain.use_case.CommentsUseCase
import com.shadow_shift_studio.aniway.domain.use_case.TitleUseCase
import com.shadow_shift_studio.aniway.model.entity.Comment
import com.shadow_shift_studio.aniway.model.entity.Title
import com.shadow_shift_studio.aniway.model.enum.ReadingStatus
import kotlinx.coroutines.launch

class MangaPageViewModel(private val context: Context) : ViewModel() {
    var id = mutableLongStateOf(0)
    val titleLiveData: MutableLiveData<Title> = MutableLiveData()
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()

    private val getTitle: TitleUseCase =
        TitleUseCase(TitleRequest())

    private val createComment: CommentsUseCase =
        CommentsUseCase(CommentsRequest())

    suspend fun getTitleComments() {
        viewModelScope.launch {
            val comments = createComment.getTitleComments(context, id.longValue, 0)

            val currentComments = commentsLiveData.value ?: emptyList()
            val updatedComments = currentComments.toMutableList()
            updatedComments.addAll(comments)

            commentsLiveData.value = updatedComments
        }
    }

    suspend fun getTitle() {
        viewModelScope.launch {
            val title = getTitle.getTitle(context, id.longValue)
            titleLiveData.value = title
        }.join()
    }

    suspend fun setReadingStatus(readingStatus: ReadingStatus) {
        viewModelScope.launch {
            val response = getTitle.setTitleReadingStatus(context, AuthorizedUser.username, id.longValue, readingStatus)
        }
    }
}