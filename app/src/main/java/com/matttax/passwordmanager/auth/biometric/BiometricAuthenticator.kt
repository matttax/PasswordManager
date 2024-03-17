package com.matttax.passwordmanager.auth.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class BiometricAuthenticator @Inject constructor(
    @ActivityContext private val context: Context
) {
    private val biometricManager = BiometricManager.from(context)

    fun auth(
        onSuccess: () -> Unit,
        onFailure: (BioAuthFailureReason) -> Unit,
        title: String? = null,
        description: String? = null,
        negativeButtonText: String? = null
    ) {
        val activity = context as? FragmentActivity ?: run {
            onFailure(BioAuthFailureReason.NOT_AVAILABLE)
            return
        }
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                authInternal(activity, onSuccess, onFailure, title, description, negativeButtonText)
            else -> onFailure(BioAuthFailureReason.NOT_AVAILABLE)
        }
    }

    private fun authInternal(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onFailure: (BioAuthFailureReason) -> Unit,
        title: String? = null,
        description: String? = null,
        negativeButtonText: String? = null
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            BiometricAuthCallback(onSuccess, onFailure)
        )
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title ?: DEFAULT_TITLE)
            .setDescription(description ?: DEFAULT_DESCRIPTION)
            .setNegativeButtonText(negativeButtonText ?: DEFAULT_NEGATIVE_BUTTON_COLOR)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    companion object {
        const val DEFAULT_NEGATIVE_BUTTON_COLOR = "Cancel"
        const val DEFAULT_DESCRIPTION = "Log in with your fingerprint"
        const val DEFAULT_TITLE = "Biometric authentication"
    }
}
