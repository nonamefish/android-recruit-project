package com.hahow.androidRecruitProject.ui.course

import com.hahow.androidRecruitProject.domain.model.course.Course
import androidx.compose.runtime.State

data class CourseUiState(
    val courses: State<List<Course>>
)