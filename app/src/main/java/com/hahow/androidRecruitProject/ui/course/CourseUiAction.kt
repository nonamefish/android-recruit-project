package com.hahow.androidRecruitProject.ui.course

sealed interface CourseUiAction {
    data object OnCreate : CourseUiAction
    data class OnClickMore(val id: String) : CourseUiAction
    data class NavigateToCourse(val id: String) : CourseUiAction
}