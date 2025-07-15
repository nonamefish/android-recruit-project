package com.hahow.androidRecruitProject.data.loader

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hahow.androidRecruitProject.MyApplication.Companion.appContext
import com.hahow.androidRecruitProject.domain.model.course.Course
import java.io.InputStreamReader

class AssetCourseDataLoader(private val assetFileName: String) : CourseDataLoader {
    override fun loadCourses(): List<Course> {
        val inputStream = appContext.assets.open(assetFileName)
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Course>>() {}.type
        return Gson().fromJson(reader, type)
    }
}