package com.matttax.passwordmanager.auth.biometric

enum class BioAuthFailureReason {
    NO_SPACE,
    TIMEOUT,
    CANCELLED,
    NOT_AVAILABLE,
    TOO_MANY_ATTEMPTS,
    UNAUTHORIZED
}
