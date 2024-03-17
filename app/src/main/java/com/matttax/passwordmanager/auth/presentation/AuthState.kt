package com.matttax.passwordmanager.auth.presentation

import com.matttax.passwordmanager.auth.data.AuthMethod


sealed interface AuthState {
    object NewUser: AuthState
    object Successful: AuthState
    data class LogIn(val method: AuthMethod): AuthState
}
