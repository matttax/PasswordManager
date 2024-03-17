package com.matttax.passwordmanager.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.matttax.passwordmanager.auth.data.AuthMethod
import com.matttax.passwordmanager.auth.presentation.AuthViewModel
import com.matttax.passwordmanager.ui.common.CheckboxOption
import com.matttax.passwordmanager.ui.common.PasswordInputField

@Composable
fun NewUserAuthScreen(authViewModel: AuthViewModel) {
    var preferBiometry by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Text(
            text = "Create your master-password to proceed",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
        PasswordInputField(
            modifier = Modifier.fillMaxWidth(0.8f),
            interactionSource = interactionSource,
            onDone = {
                focusManager.clearFocus()
                authViewModel.apply {
                    onNewPassword(it)
                    if (preferBiometry) {
                        onPreferredAuthMethodChanged(AuthMethod.BIOMETRIC)
                    }
                }
            }
        )
        CheckboxOption(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = "Prefer biometry",
            isChecked = preferBiometry,
            onCheck = {
                preferBiometry = it
            }
        )
    }
}
