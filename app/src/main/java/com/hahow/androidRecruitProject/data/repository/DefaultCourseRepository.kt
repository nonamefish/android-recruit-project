package com.hahow.androidRecruitProject.data.repository

import com.hahow.androidRecruitProject.data.loader.CourseDataLoader
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.domain.repository.CourseRepository

class DefaultCourseRepository(
    private val loader: CourseDataLoader
) : CourseRepository {
    override suspend fun getCourses(): List<Course> {
        return loader.loadCourses()
    }
}