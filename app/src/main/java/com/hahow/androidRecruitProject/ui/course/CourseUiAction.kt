package com.hahow.androidRecruitProject.ui.course

sealed interface CourseUiAction {
    data object OnCreate : CourseUiAction
}