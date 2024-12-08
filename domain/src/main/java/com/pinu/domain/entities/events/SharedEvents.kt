package com.pinu.domain.entities.events

import com.pinu.domain.entities.ToastMessage

sealed interface SharedEvents {
    data object ClearToastMessage : SharedEvents
    data class ShowToastMessage(val toastMessage: ToastMessage) : SharedEvents

}