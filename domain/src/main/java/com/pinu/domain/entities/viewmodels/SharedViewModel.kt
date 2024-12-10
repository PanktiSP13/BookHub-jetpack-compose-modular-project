package com.pinu.domain.entities.viewmodels

import androidx.lifecycle.ViewModel
import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.ToastMessageType
import com.pinu.domain.entities.events.SharedEvents
import com.pinu.domain.entities.states.SharedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _sharedState = MutableStateFlow(SharedState())
    val sharedState = _sharedState.asStateFlow()

    fun onEvent(sharedEvents: SharedEvents) {
        when (sharedEvents) {
            is SharedEvents.ClearToastMessage -> clearToast()
            is SharedEvents.ShowToastMessage -> showToast(sharedEvents.toastMessage)
        }
    }

    private fun clearToast() {
        _sharedState.update {
            it.copy(
                toastMessage = ToastMessage(
                    type = ToastMessageType.NONE,
                    message = ""
                )
            )
        }
    }

    private fun showToast(toastMessage: ToastMessage) {
        _sharedState.update { it.copy(toastMessage = toastMessage) }
    }
}