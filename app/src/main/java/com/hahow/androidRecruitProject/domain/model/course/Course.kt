package com.hahow.androidRecruitProject.domain.model.course

import com.hahow.androidRecruitProject.domain.model.assignment.Assignment


data class Course(
    val id: String,
    val title: String,
    val coverImageUrl: String?,
    val totalSeconds: Int?,
    val enrollmentsCount: Int?,
    val averageRating: Float?,
    val level: String?,
    val completionStatus: String?,
    val completionPercentage: Float?,
    val source: CourseSource?,
    val studiedAt: String?,
    val enrolled: Boolean?,
    val teacher: Teacher?,
    val recentStartedAssignment: Assignment?,
    val lastViewedAt: String?,
    val accessExpiredReason: String?
)