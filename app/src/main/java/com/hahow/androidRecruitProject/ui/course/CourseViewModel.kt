package com.hahow.androidRecruitProject.ui.course

import androidx.lifecycle.viewModelScope
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repo: DefaultCourseRepository,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CourseUiState(loadingState = LoadingState.Idle))
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    fun onUiAction(action: CourseUiAction) {
        when (action) {
            CourseUiAction.OnCreate -> getCourses()
        }
    }


    fun getCourses() {
        _uiState.update { it.copy(loadingState = LoadingState.Loading) }

        viewModelScope.launch {
            val courses = repo.getCourses()
            _uiState.update {
                it.copy(
                    courses = courses,
                    loadingState = LoadingState.Idle,
                )
            }
        }
    }
}