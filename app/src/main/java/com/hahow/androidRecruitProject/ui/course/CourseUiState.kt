package com.hahow.androidRecruitProject.ui.course

data class CourseUiState(
    val courses: List<CourseUiItem> = emptyList(),
    val loadingState: LoadingState,
)

// UI 友善的数据结构，ViewModel 负责填充所有展示用字段
// 颜色用 UiColor 表示语义色，Compose 层再映射为实际 Color

data class CourseUiItem(
    val id: String,
    val title: String,
    val coverImageUrl: String?,
    val progressPercent: Int,
    val studiedDateText: String?,
    val progressBarText: String,
    val deadlineText: String?,
    val isCompulsory: Boolean,
    val imageBadgeText: String?,
    val imageTagType: TagType?,
    val imageTagText: String?,
    val titleBadgeText: String?
)


sealed class TagType {
    object Assigner : TagType()
    object LastViewed : TagType()
}

enum class LoadingState {
    Idle,
    Loading,
    Error
}