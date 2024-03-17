package com.matttax.passwordmanager.passwords.domain

import kotlinx.serialization.Serializable

@Serializable
data class Passwords(
    val list: List<PasswordData> = emptyList()
)
