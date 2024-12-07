package com.pinu.domain.entities

data class ToastMessage(
    val type: ToastMessageType = ToastMessageType.NONE,
    val message: String = ""
)

enum class ToastMessageType {
    NONE, ERROR, SUCCESS, WARNING, INFORMATION
}