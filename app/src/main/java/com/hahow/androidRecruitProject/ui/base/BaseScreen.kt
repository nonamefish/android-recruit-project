package com.hahow.androidRecruitProject.ui.base

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hahow.androidRecruitProject.ui.component.dialog.BaseDialog

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel,
    onEventCollect: (event: BaseViewModel.BaseEvent) -> Unit = {},
    shouldCollectEvents: Boolean = true,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    val content = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        if (shouldCollectEvents) {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is BaseViewModel.Event.ShowToast ->
                        Toast.makeText(content, event.message, Toast.LENGTH_SHORT).show()
                    else -> Unit
                }
                onEventCollect(event)
            }
        }
    }

    val dialogUiState by viewModel.dialogUiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        content()
        BaseDialog(dialogUiState)
    }
}