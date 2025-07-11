package com.hahow.androidRecruitProject.ui.course

import com.hahow.androidRecruitProject.domain.model.course.Course
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow

data class CourseUiState(
    val courses: List<Course> = emptyList(),
    val loadingState: LoadingState,
)

enum class LoadingState {
    Idle,
    Loading,
    Error
}