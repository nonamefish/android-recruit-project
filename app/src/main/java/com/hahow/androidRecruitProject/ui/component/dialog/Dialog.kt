package com.hahow.androidRecruitProject.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hahow.androidRecruitProject.ui.component.BoxButton
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.theme.HahowTypography

@Composable
fun BaseDialog(
    openDialog: MutableState<Boolean> = mutableStateOf(false),
    title: String? = null,
    message: String? = null,
    confirmText: String? = null,
    cancelText: String? = null,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    dialogProperties: DialogProperties? = null,
) {
    if (!openDialog.value) return

    Dialog(
        onDismissRequest = {
            openDialog.value = false
        },
        properties = dialogProperties ?: DialogProperties()
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)) {
            Column(
                modifier = Modifier
                    .background(HahowColor.background, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(36.dp))

                Text(
                    text = title ?: "",
                    style = HahowTypography.body01,
                    color = HahowColor.hahow_gray_800,
                    textAlign = TextAlign.Start
                )

                message?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = it,
                        style = HahowTypography.body01,
                        color = HahowColor.hahow_gray_800,
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(Modifier.height(36.dp))

                BoxButton(
                    text = confirmText ?: "",
                    textStyle = HahowTypography.subtitle01,
                    enable = true,
                    onClick = {
                        onConfirm()
                        openDialog.value = false
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )

                cancelText?.let {
                    BoxButton(
                        text = cancelText,
                        textStyle = HahowTypography.subtitle01,
                        enable = true,
                        onClick = {
                            onCancel()
                            openDialog.value = false
                        }
                    )
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BaseDialog(
    dialogUiState: DialogUiState,
) {
    BaseDialog(
        openDialog = dialogUiState.openDialog,
        title = dialogUiState.title,
        message = dialogUiState.message,
        confirmText = dialogUiState.confirmText,
        cancelText = dialogUiState.cancelText,
        onConfirm = dialogUiState.onConfirm,
        onCancel = dialogUiState.onCancel,
        dialogProperties = dialogUiState.dialogProperties
    )
}