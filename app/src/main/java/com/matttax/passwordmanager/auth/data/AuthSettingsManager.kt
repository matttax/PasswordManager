package com.matttax.passwordmanager.auth.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthSettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    var preferredAuthMethod: AuthMethod
        get() {
            return when(sharedPrefs.getString(AUTH_METHOD_KEY, null)) {
                AuthMethod.BIOMETRIC.stringName -> AuthMethod.BIOMETRIC
                AuthMethod.PASSWORD.stringName -> AuthMethod.PASSWORD
                else -> AuthMethod.NONE
            }
        }
        set(value) {
            sharedPrefs.edit().putString(AUTH_METHOD_KEY, value.stringName).apply()
        }

    companion object {
        const val PREFS_KEY = "auth_prefs"
        const val AUTH_METHOD_KEY = "auth_method"
    }
}
