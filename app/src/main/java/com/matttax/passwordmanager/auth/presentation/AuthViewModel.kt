package com.matttax.passwordmanager.auth.presentation

import androidx.lifecycle.ViewModel
import com.matttax.passwordmanager.auth.data.AuthMethod
import com.matttax.passwordmanager.auth.domain.AuthManager
import com.matttax.passwordmanager.auth.data.AuthSettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val authSettingsManager: AuthSettingsManager
) : ViewModel() {

    private val preferredAuthType = authSettingsManager.preferredAuthMethod

    private val _authState = MutableStateFlow(getInitialAuthState())
    val authState = _authState.asStateFlow()

    fun onBiometricAuth(isSuccessful: Boolean) {
        _authState.value = if (isSuccessful) {
             AuthState.Successful
        } else {
            AuthState.LogIn(AuthMethod.PASSWORD)
        }
    }

    fun onPasswordAuth(password: String) {
        _authState.value = when(authManager.checkPassword(password)) {
            PasswordCheckState.CORRECT ->  AuthState.Successful
            PasswordCheckState.INCORRECT -> AuthState.LogIn(AuthMethod.PASSWORD)
            PasswordCheckState.NO_PASSWORD -> AuthState.NewUser
        }
    }

    fun onNewPassword(password: String) {
        authManager.setNewPassword(password)
        _authState.value = AuthState.Successful
    }

    fun onPreferredAuthMethodChanged(authMethod: AuthMethod) {
        authSettingsManager.preferredAuthMethod = authMethod
    }

    private fun getInitialAuthState(): AuthState {
        return if (authManager.passwordExists) {
            when(preferredAuthType) {
                AuthMethod.BIOMETRIC -> AuthState.LogIn(preferredAuthType)
                else -> AuthState.LogIn(AuthMethod.PASSWORD)
            }
        } else AuthState.NewUser
    }
}
