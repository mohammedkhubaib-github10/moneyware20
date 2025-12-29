package com.example.presentation.ui_event

sealed interface UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent
}
