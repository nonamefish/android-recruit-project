package com.hahow.androidRecruitProject.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    abstract class BaseEvent
    /** One-shot UI Event System
     * BaseEvent 是所有事件的基類，繼承自 BaseViewModel.BaseEvent
     * 這樣可以確保所有事件都能被 BaseViewModel 處理，共用事件就在這邊定義
     */
    sealed class Event : BaseEvent() {
        data class ShowSnackBar(val message: String) : Event()
    }

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
}