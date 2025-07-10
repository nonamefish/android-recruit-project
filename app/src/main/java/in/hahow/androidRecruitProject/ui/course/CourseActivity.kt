package `in`.hahow.androidRecruitProject.ui.course

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import `in`.hahow.androidRecruitProject.ui.base.BaseScreen

class CourseActivity : ComponentActivity() {

    private val courseViewModel =  CourseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(
                viewModel = courseViewModel,
                onEventCollect = {

                }
            ) {
                LazyColumn() {

                }
            }
        }
    }
}

