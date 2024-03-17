package com.matttax.passwordmanager.encryption.cipher

import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DecryptCipherProvider @Inject constructor(
    private val secretKeyProvider: SecretKeyProvider,
    private val encryptionConfig: EncryptionConfig
) {
    fun getDecryptCipher(iv: ByteArray): Cipher {
        return Cipher.getInstance(encryptionConfig.transformation).apply {
            init(Cipher.DECRYPT_MODE, secretKeyProvider.getKey(), IvParameterSpec(iv))
        }
    }
}
