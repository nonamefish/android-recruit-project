package com.hahow.androidRecruitProject.domain.repository

import com.hahow.androidRecruitProject.domain.model.course.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
}