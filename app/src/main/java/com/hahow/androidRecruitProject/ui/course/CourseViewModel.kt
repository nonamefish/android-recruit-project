package com.hahow.androidRecruitProject.ui.course

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repo: DefaultCourseRepository,
) : BaseViewModel() {

    val uiState by lazy {
        CourseUiState(
            courses = courses
        )
    }

    private val courses = mutableStateOf<List<Course>>(emptyList())

    fun onUiAction(action: CourseUiAction) {
        when (action) {
            CourseUiAction.OnCreate -> getCourses()
        }
    }

    fun getCourses() {
        viewModelScope.launch {
            courses.value = repo.getCourses()
            Log.d("CourseViewModel", "Courses loaded: ${courses.value.size}")
        }
    }
}