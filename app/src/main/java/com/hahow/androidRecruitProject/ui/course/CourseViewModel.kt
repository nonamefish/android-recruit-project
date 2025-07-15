package com.hahow.androidRecruitProject.ui.course

import androidx.lifecycle.viewModelScope
import com.hahow.androidRecruitProject.R
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.domain.model.assignment.Assigner
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

                val dueAt = course.recentStartedAssignment?.timeline?.dueAt
                val (percent, progressBarText) = getProgressData(course.completionPercentage)
                val (imageTagType, imageTagText) = getImageTagDate(
                    course.recentStartedAssignment?.assigners,
                    course.lastViewedAt
                )
                CourseUiItem(
                    id = course.id,
                    title = course.title,
                    coverImageUrl = course.coverImageUrl,
                    progressPercent = percent,
                    progressBarText = progressBarText,
                    studiedDateText = getStudiedDateText(course.studiedAt),
                    deadlineText = getDeadlineText(dueAt, course.studiedAt),
                    isCompulsory = isCompulsory(course.recentStartedAssignment?.rule),
                    imageBadgeText = getImageBadgeText(course.source),
                    imageTagType = imageTagType,
                    imageTagText = imageTagText,
                    titleBadgeText = getTitleBadgeText(course.recentStartedAssignment?.rule)
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

    private fun getImageTagDate(
        assigners: List<Assigner>?,
        lastViewedAt: String?
    ): Pair<TagType?, String> {
        val assignerName = assigners?.firstOrNull()?.name
        return when {
            assignerName != null -> {
                TagType.Assigner to
                        ResourceUtil.getString(R.string.course_assigner_tag, assignerName)
            }

            lastViewedAt != null -> {
                val daysAgo = abs(DateUtil.daysFromNow(lastViewedAt))
                TagType.LastViewed to
                        ResourceUtil.getString(R.string.course_last_viewed_tag, daysAgo)
            }

            else -> null to ""
        }
    }

    private fun getStudiedDateText(studiedAt: String?): String? {
        return if (studiedAt == null) {
            null
        } else {
            val date = DateUtil.extractDateFromIso(studiedAt)
            ResourceUtil.getString(R.string.course_passed, date ?: "")
        }
    }

    private fun getProgressData(completionPercentage: Float?): Pair<Int, String> {
        val percent = ((completionPercentage ?: 0f) * 100).toInt()
        val barText = ResourceUtil.getString(R.string.course_percentage, percent)
        return Pair(percent, barText)
    }

    private fun isCompulsory(rule: Rule?) = rule == Rule.COMPULSORY

    private fun getImageBadgeText(courseSource: CourseSource?) =
        if (courseSource == CourseSource.TENANT_COURSE) {
            ResourceUtil.getString(R.string.course_type_tenant)
        } else {
            null
        }

    private fun getTitleBadgeText(rule: Rule?) = when (rule) {
        Rule.COMPULSORY -> ResourceUtil.getString(R.string.course_rule_compulsory)
        Rule.ELECTIVE -> ResourceUtil.getString(R.string.course_rule_elective)
        else -> null
    }

    private fun getDeadlineText(dueAt: String?, studiedAt: String?): String? {
        if (studiedAt != null) return null
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