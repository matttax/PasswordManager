package com.matttax.passwordmanager.auth.biometric

import androidx.biometric.BiometricPrompt

class BiometricAuthCallback(
    private val onSuccess: () -> Unit,
    private val onFailure: (BioAuthFailureReason) -> Unit
) : BiometricPrompt.AuthenticationCallback() {

    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
        onSuccess()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
        onFailure(BioAuthErrorMapper.mapErrors(errorCode))
    }

    override fun onAuthenticationFailed() {
        onFailure(BioAuthFailureReason.UNAUTHORIZED)
    }
}
