package com.matttax.passwordmanager.auth.biometric

import androidx.biometric.BiometricPrompt
import java.lang.IllegalArgumentException

object BioAuthErrorMapper {

    fun mapErrors(
        @BiometricPrompt.AuthenticationError errorCode: Int
    ): BioAuthFailureReason {
        return when (errorCode) {
            BiometricPrompt.ERROR_HW_NOT_PRESENT,
            BiometricPrompt.ERROR_NO_BIOMETRICS,
            BiometricPrompt.ERROR_HW_UNAVAILABLE,
            BiometricPrompt.ERROR_VENDOR,
            BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> BioAuthFailureReason.NOT_AVAILABLE

            BiometricPrompt.ERROR_CANCELED,
            BiometricPrompt.ERROR_USER_CANCELED,
            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> BioAuthFailureReason.CANCELLED

            BiometricPrompt.ERROR_LOCKOUT,
            BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> BioAuthFailureReason.TOO_MANY_ATTEMPTS

            BiometricPrompt.ERROR_TIMEOUT -> BioAuthFailureReason.TIMEOUT

            BiometricPrompt.ERROR_NO_SPACE -> BioAuthFailureReason.NO_SPACE

            BiometricPrompt.ERROR_UNABLE_TO_PROCESS -> BioAuthFailureReason.UNAUTHORIZED

            else -> throw UnknownBiometricAuthError()
        }
    }

    class UnknownBiometricAuthError : IllegalArgumentException("Unknown error code")
}
