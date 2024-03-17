package com.matttax.passwordmanager.auth.presentation.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.matttax.passwordmanager.auth.biometric.BiometricAuthenticator
import com.matttax.passwordmanager.auth.presentation.AuthState
import com.matttax.passwordmanager.auth.presentation.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    biometricAuthenticator: BiometricAuthenticator,
    onSuccess: (isNew: Boolean) -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    var isNewUser by rememberSaveable { mutableStateOf(false) }
    when (val state = authState) {
        is AuthState.Successful -> onSuccess(isNewUser)
        is AuthState.NewUser -> {
            isNewUser = true
            NewUserAuthScreen(authViewModel)
        }
        is AuthState.LogIn -> LogInScreen(
            authMethod = state.method,
            biometricAuthenticator = biometricAuthenticator,
            onBioAuthFinished = authViewModel::onBiometricAuth,
            onPasswordAuth = authViewModel::onPasswordAuth
        )
    }
}
