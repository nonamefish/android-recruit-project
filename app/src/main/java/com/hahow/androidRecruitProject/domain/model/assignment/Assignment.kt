package com.hahow.androidRecruitProject.domain.model.assignment

data class Assignment(
    val id: String,
    val title: String?,
    val assigners: List<Assigner>?,
    val rule: Rule?,
    val completedAt: String?,
    val timeline: AssignmentTimeline?
)