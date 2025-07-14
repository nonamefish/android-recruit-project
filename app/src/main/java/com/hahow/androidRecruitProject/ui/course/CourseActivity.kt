package com.hahow.androidRecruitProject.ui.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hahow.androidRecruitProject.ui.base.BaseScreen
import com.hahow.androidRecruitProject.ui.course.widget.CourseItemScreen
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.util.LifecycleHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseActivity : ComponentActivity() {

    private val courseViewModel: CourseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BaseScreen(
                modifier = Modifier,
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

                LazyColumn(
                    modifier = Modifier
                        .background(HahowColor.background)
                        .systemBarsPadding()
                ) {
                    items(uiState.courses) { course ->
                        CourseItemScreen(course)
                    }
                }
            }
        }
    }
}

