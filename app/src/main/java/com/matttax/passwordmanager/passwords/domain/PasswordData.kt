package com.matttax.passwordmanager.passwords.domain

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PasswordData(
    val id: String = UUID.randomUUID().toString(),
    val login: String? = null,
    val password: String? = null,
    val webpageUrl: String? = null,
    val iconUri: String? = null
)
