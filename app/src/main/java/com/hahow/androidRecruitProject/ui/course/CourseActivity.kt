package com.hahow.androidRecruitProject.ui.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.Lifecycle
import com.hahow.androidRecruitProject.ui.base.BaseScreen
import com.hahow.androidRecruitProject.ui.util.LifecycleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.foundation.lazy.items
import com.hahow.androidRecruitProject.ui.course.widget.Course

class CourseActivity : ComponentActivity() {

    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BaseScreen(
                viewModel = courseViewModel,
                onEventCollect = {

                }
            ) {
                val uiState by courseViewModel.uiState.collectAsStateWithLifecycle()

                LifecycleHelper().OnLifecycleEvent { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_CREATE -> {
                            courseViewModel.onUiAction(CourseUiAction.OnCreate)
                        }
                        else -> Unit
                    }
                }

                LazyColumn{
                    items(uiState.courses) { course ->
                        Course(course)
                    }
                }
            }
        }
    }
}

