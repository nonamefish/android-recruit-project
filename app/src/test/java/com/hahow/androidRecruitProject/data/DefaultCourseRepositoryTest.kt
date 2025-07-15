package com.hahow.androidRecruitProject.data

import com.hahow.androidRecruitProject.BaseTest
import com.hahow.androidRecruitProject.data.loader.CourseDataLoader
import com.hahow.androidRecruitProject.data.repository.DefaultCourseRepository
import com.hahow.androidRecruitProject.domain.model.assignment.Assigner
import com.hahow.androidRecruitProject.domain.model.assignment.Assignment
import com.hahow.androidRecruitProject.domain.model.assignment.AssignmentTimeline
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.domain.model.course.CourseSource
import com.hahow.androidRecruitProject.domain.model.course.Teacher
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DefaultCourseRepositoryTest : BaseTest() {

    private lateinit var loader: CourseDataLoader
    private lateinit var repository: DefaultCourseRepository

    @Before
    fun setup() {
        super.setUp()
        loader = mockk()
        repository = DefaultCourseRepository(loader)
    }

    @Test
    fun `getCourses returns courses from loader`() = runTest {
        // Given
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

        // When
        coEvery { loader.loadCourses() } returns fakeCourses

        // Then
        val result = repository.getCourses()
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Test Course", result[0].title)
    }
}