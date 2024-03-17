package com.matttax.passwordmanager.auth.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.matttax.passwordmanager.auth.biometric.BiometricAuthenticator
import com.matttax.passwordmanager.auth.presentation.AuthState
import com.matttax.passwordmanager.auth.presentation.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    biometricAuthenticator: BiometricAuthenticator,
    onSuccess: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()
    when (val state = authState) {
        is AuthState.Successful -> onSuccess()
        is AuthState.NewUser -> NewUserAuthScreen(authViewModel)
        is AuthState.LogIn -> LogInScreen(
            authMethod = state.method,
            biometricAuthenticator = biometricAuthenticator,
            onBioAuthFinished = authViewModel::onBiometricAuth,
            onPasswordAuth = authViewModel::onPasswordAuth
        )
    }
}
