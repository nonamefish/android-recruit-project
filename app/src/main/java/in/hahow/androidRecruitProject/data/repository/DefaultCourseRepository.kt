package `in`.hahow.androidRecruitProject.data.repository

import `in`.hahow.androidRecruitProject.data.loader.CourseDataLoader
import `in`.hahow.androidRecruitProject.domain.model.course.Course
import `in`.hahow.androidRecruitProject.domain.repository.CourseRepository

class DefaultCourseRepository(
    private val loader: CourseDataLoader
) : CourseRepository {
    override suspend fun getCourses(): List<Course> {
        return loader.loadCourses()
    }
}