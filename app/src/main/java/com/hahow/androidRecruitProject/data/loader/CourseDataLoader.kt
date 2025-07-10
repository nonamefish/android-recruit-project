package com.hahow.androidRecruitProject.data.loader

import com.hahow.androidRecruitProject.domain.model.course.Course

interface CourseDataLoader {
    fun loadCourses(): List<Course>
}