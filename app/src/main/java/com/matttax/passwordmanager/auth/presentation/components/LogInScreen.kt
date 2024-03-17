package com.matttax.passwordmanager.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.matttax.passwordmanager.auth.biometric.BiometricAuthenticator
import com.matttax.passwordmanager.auth.data.AuthMethod
import com.matttax.passwordmanager.ui.common.PasswordInputField

@Composable
fun LogInScreen(
    authMethod: AuthMethod,
    biometricAuthenticator: BiometricAuthenticator,
    onBioAuthFinished: (Boolean) -> Unit,
    onPasswordAuth: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    if (authMethod == AuthMethod.BIOMETRIC) {
        biometricAuthenticator.auth(
            onSuccess = { onBioAuthFinished(true) },
            onFailure = { onBioAuthFinished(false) }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Enter your master-password",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        PasswordInputField(
            modifier = Modifier.fillMaxWidth(0.8f),
            interactionSource = interactionSource,
            onDone = {
                focusManager.clearFocus()
                onPasswordAuth(it)
            }
        )
    }
}
