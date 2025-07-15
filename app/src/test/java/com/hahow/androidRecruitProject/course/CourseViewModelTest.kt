package com.hahow.androidRecruitProject.course

import app.cash.turbine.test
import com.hahow.androidRecruitProject.BaseTest
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.domain.model.assignment.Assigner
import com.hahow.androidRecruitProject.domain.model.assignment.Assignment
import com.hahow.androidRecruitProject.domain.model.assignment.AssignmentTimeline
import com.hahow.androidRecruitProject.domain.model.assignment.Rule
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.domain.model.course.CourseSource
import com.hahow.androidRecruitProject.domain.model.course.Teacher
import com.hahow.androidRecruitProject.ui.base.BaseViewModel
import com.hahow.androidRecruitProject.ui.course.CourseUiAction
import com.hahow.androidRecruitProject.ui.course.CourseViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CourseViewModelTest : BaseTest() {
    private lateinit var repository: DefaultCourseRepository
    private lateinit var viewModel: CourseViewModel

    @Before
    fun setup() {
        super.setUp()
        repository = mockk()
        viewModel = CourseViewModel(repository)
    }

    @Test
    fun `getCourses updates uiState with courses`() = runTest {
        // Given
        val fakeCourses = fakeCourses
        coEvery { repository.getCourses() } returns fakeCourses

        // When
        viewModel.getCourses()
        advanceUntilIdle()

        // Then
        val finalState = viewModel.uiState.value
        assertEquals(1, finalState.courses.size)
        assertEquals("Test Course", finalState.courses[0].title)
    }

    @Test
    fun `course with compulsory rule maps isCompulsory true and badge text`() = runTest {
        // Given
        val fakeCourses = fakeCourses.map {
            it.copy(
                recentStartedAssignment = it.recentStartedAssignment?.copy(rule = Rule.COMPULSORY)
            )
        }
        coEvery { repository.getCourses() } returns fakeCourses

        // When
        viewModel.getCourses()
        advanceUntilIdle()

        // Then
        val item = viewModel.uiState.value.courses[0]
        assertEquals(true, item.isCompulsory)
        assertEquals(true, item.titleBadgeText != null)
        assertEquals("企業課程", item.imageBadgeText)
    }

    @Test
    fun `course with deadline in the future maps correct deadlineText`() = runTest {
        // Given
        val dueAt = "2099-12-31T00:00:00Z"
        val fakeCourses = fakeCourses.map {
            it.copy(
                recentStartedAssignment = it.recentStartedAssignment?.copy(
                    timeline = AssignmentTimeline(startAt = null, dueAt = dueAt)
                )
            )
        }
        coEvery { repository.getCourses() } returns fakeCourses

        // When
        viewModel.getCourses()
        advanceUntilIdle()

        // Then
        val item = viewModel.uiState.value.courses[0]
        assertEquals("2099-12-31 截止", item.deadlineText)
    }

    @Test
    fun `onUiAction OnClickMore emits ShowToast event`() = runTest {
        // Given
        val courseId = "1"
        viewModel.eventsFlow.test {
            // When
            viewModel.onUiAction(CourseUiAction.OnClickMore(courseId))
            // Then
            val event = awaitItem()
            assert(event is BaseViewModel.Event.ShowToast)
            assert((event as BaseViewModel.Event.ShowToast).message.contains(courseId))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onUiAction NavigateToCourse emits NavigateToCourse event`() = runTest {
        // Given
        val fakeCourses = fakeCourses
        coEvery { repository.getCourses() } returns fakeCourses
        viewModel.getCourses()
        advanceUntilIdle()
        val courseId = fakeCourses[0].id
        viewModel.eventsFlow.test {
            // When
            viewModel.onUiAction(CourseUiAction.NavigateToCourse(courseId))
            // Then
            val event = awaitItem()
            assert(event is CourseViewModel.CourseEvent.NavigateToCourse)
            assert((event as CourseViewModel.CourseEvent.NavigateToCourse).course.id == courseId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    val fakeCourses = listOf(
        Course(
            id = "1",
            title = "Test Course",
            coverImageUrl = null,
            totalSeconds = 1000,
            enrollmentsCount = 10,
            averageRating = 4.5f,
            level = "Beginner",
            completionStatus = "COMPLETED",
            completionPercentage = 1.0f,
            source = CourseSource.TENANT_COURSE,
            studiedAt = null,
            enrolled = true,
            teacher = Teacher(name = "John Doe"),
            recentStartedAssignment = Assignment(
                id = "a1",
                title = "Assignment 1",
                assigners = listOf(Assigner(id = "t1", name = "Teacher A")),
                rule = null,
                completedAt = null,
                timeline = AssignmentTimeline(startAt = null, dueAt = null)
            ),
            lastViewedAt = null,
            accessExpiredReason = null
        )
    )
}