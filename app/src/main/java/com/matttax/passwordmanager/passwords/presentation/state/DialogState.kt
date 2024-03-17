package com.matttax.passwordmanager.passwords.presentation.state

sealed interface DialogState {
    object Add : DialogState
    data class Remove(val id: String) : DialogState
}
