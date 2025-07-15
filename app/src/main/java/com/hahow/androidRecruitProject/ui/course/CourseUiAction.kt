package com.hahow.androidRecruitProject.ui.course

sealed interface CourseUiAction {
    data object OnCreate : CourseUiAction
    data class OnClickMore(val courseId: String) : CourseUiAction
    data class NavigateToCourse(val courseId: String) : CourseUiAction
}