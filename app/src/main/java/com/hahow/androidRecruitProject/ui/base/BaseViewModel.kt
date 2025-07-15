package com.hahow.androidRecruitProject.ui.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hahow.androidRecruitProject.R
import com.hahow.androidRecruitProject.ui.component.dialog.DialogUiState
import com.hahow.androidRecruitProject.util.ResourceUtil
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    abstract class BaseEvent

    /** One-shot UI Event System
     * BaseEvent 是所有事件的基類，確保所有事件都能被 BaseViewModel 處理，共用事件就在這邊定義
     * 譬如：啟動外部 Intent(打電話、開啟瀏覽器、分享內容、開啟相機等)或導航等
     */
    sealed class Event : BaseEvent() {
        data class ShowToast(val message: String) : Event()
    }

    private val _dialogUiState = MutableStateFlow(DialogUiState())
    val dialogUiState = _dialogUiState.asStateFlow()

    private val _eventsFlow = MutableSharedFlow<BaseEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val eventsFlow: SharedFlow<BaseEvent> = _eventsFlow


    protected fun sendEvent(event: BaseEvent) {
        viewModelScope.launch {
            _eventsFlow.emit(event)
        }
    }

    protected fun openDialog(message: String) {
        if (message.isNotEmpty()) {
            _dialogUiState.update {
                it.copy(
                    openDialog = mutableStateOf(true),
                    message = message,
                    confirmText = ResourceUtil.getString(R.string.confirm),
                )
            }
        }
    }

    protected fun openDialog(dialogUiState: DialogUiState) {
        _dialogUiState.update {
            dialogUiState.copy(
                openDialog = mutableStateOf(true)
            )
        }
    }
}