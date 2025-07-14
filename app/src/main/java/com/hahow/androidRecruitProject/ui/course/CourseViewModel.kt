package com.hahow.androidRecruitProject.ui.course

import androidx.lifecycle.viewModelScope
import com.hahow.androidRecruitProject.R
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.domain.model.assignment.Rule
import com.hahow.androidRecruitProject.domain.model.course.CourseSource
import com.hahow.androidRecruitProject.ui.base.BaseViewModel
import com.hahow.androidRecruitProject.util.DateUtil
import com.hahow.androidRecruitProject.util.ResourceUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.ranges.contains

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
            val uiItems = courses.map { course ->
                // Progress
                val percent = ((course.completionPercentage ?: 0f) * 100).toInt()
                val progressBarText = "$percent%"

                // Studied date
                val studiedDateText = course.studiedAt?.let {
                    val date = DateUtil.extractDateFromIso(it)
                    if (date != null) "$date 通過" else null
                }

                // Deadline
                val dueAt = course.recentStartedAssignment?.timeline?.dueAt
                val rule = course.recentStartedAssignment?.rule
                val isCompulsory = rule == Rule.COMPULSORY

                // Type tag
                val imageBadgeText =
                    if (course.source == CourseSource.TENANT_COURSE) "企業課程" else null

                // Tag (assigner or last viewed)
                val assignerName = course.recentStartedAssignment?.assigners?.firstOrNull()?.name
                val (imageTagType, imageTagText) = when {
                    assignerName != null -> TagType.Assigner to "$assignerName 指派"
                    course.lastViewedAt != null -> {
                        val daysAgo = abs(DateUtil.daysFromNow(course.lastViewedAt))
                        TagType.LastViewed to "$daysAgo 天前觀看"
                    }

                    else -> null to ""
                }

                val days = DateUtil.daysFromNow(dueAt ?: "")
                val deadlineText = when {
                    course.studiedAt != null -> null
                    dueAt.isNullOrBlank() -> "無期限"
                    days < 0L -> "已逾期"
                    days == 0L -> "1 天內截止"
                    days in 1..7 -> "截止日剩 $days 天"
                    else -> "${DateUtil.extractDateFromIso(dueAt)} 截止"
                }

                val titleBadgeText = when (rule) {
                    Rule.COMPULSORY -> "必修"
                    Rule.ELECTIVE -> "推薦"
                    else -> null
                }

                CourseUiItem(
                    id = course.id,
                    title = course.title,
                    coverImageUrl = course.coverImageUrl,
                    progressPercent = percent,
                    studiedDateText = studiedDateText,
                    progressBarText = progressBarText,
                    deadlineText = deadlineText,
                    isCompulsory = isCompulsory,
                    imageBadgeText = imageBadgeText,
                    imageTagType = imageTagType,
                    imageTagText = imageTagText,
                    titleBadgeText = titleBadgeText
                )
            }
            _uiState.update {
                it.copy(
                    courses = uiItems,
                    loadingState = LoadingState.Idle,
                )
            }
        }
    }

    private fun getDeadlineText(dueAt: String?): String {
        if (dueAt.isNullOrBlank()) return ResourceUtil.getString(R.string.course_no_expiry_date)

        val days = DateUtil.daysFromNow(dueAt)
        return when {
            days < 0L -> ResourceUtil.getString(R.string.course_deadline_overdue)
            days == 0L -> ResourceUtil.getString(R.string.course_deadline_within_one_day)
            days in 1..7 -> ResourceUtil.getString(R.string.course_deadline_days_left, days)
            else -> ResourceUtil.getString(
                R.string.course_deadline_end,
                DateUtil.extractDateFromIso(dueAt) ?: ""
            )
        }
    }
}