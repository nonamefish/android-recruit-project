package com.hahow.androidRecruitProject.ui.component.dialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.DialogProperties

data class DialogUiState(
    val openDialog: MutableState<Boolean> = mutableStateOf(false),
    val title: String? = null,
    val message: String? = null,
    val confirmText: String? = null,
    val cancelText: String? = null,
    val onConfirm: () -> Unit = {},
    val onCancel: () -> Unit = {},
    val dialogProperties: DialogProperties? = null,
)