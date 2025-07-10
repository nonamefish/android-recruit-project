package `in`.hahow.androidRecruitProject.domain.repository

import `in`.hahow.androidRecruitProject.domain.model.course.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
}