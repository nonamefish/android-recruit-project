package `in`.hahow.androidRecruitProject.data.loader

import `in`.hahow.androidRecruitProject.domain.model.course.Course

interface CourseDataLoader {
    fun loadCourses(): List<Course>
}