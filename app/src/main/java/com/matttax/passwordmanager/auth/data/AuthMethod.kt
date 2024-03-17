package com.matttax.passwordmanager.auth.data

enum class AuthMethod(val stringName: String) {
    BIOMETRIC("biometric"),
    PASSWORD("password"),
    NONE("none")
}
