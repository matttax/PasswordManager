package com.matttax.passwordmanager.encryption.cipher

import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import javax.crypto.Cipher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptCipherProvider @Inject constructor(
    private val secretKeyProvider: SecretKeyProvider,
    private val encryptionConfig: EncryptionConfig
) {
    val encryptCipher: Cipher
        get() = Cipher.getInstance(encryptionConfig.transformation).apply {
            init(Cipher.ENCRYPT_MODE, secretKeyProvider.getKey())
        }
}
