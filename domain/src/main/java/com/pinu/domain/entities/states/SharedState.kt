package com.pinu.domain.entities.states

import com.pinu.domain.entities.ToastMessage

data class SharedState(
    val toastMessage: ToastMessage = ToastMessage())