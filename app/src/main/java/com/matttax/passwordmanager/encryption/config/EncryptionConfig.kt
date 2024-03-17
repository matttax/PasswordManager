package com.matttax.passwordmanager.encryption.config

data class EncryptionConfig(
    val algorithm: String,
    val blockMode: String,
    val padding: String,
    val chunkSize: Int,
    val keySize: Int
) {
    val transformation = "$algorithm/$blockMode/$padding"
}
