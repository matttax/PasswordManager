package com.matttax.passwordmanager.encryption.cipher

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecretKeyProvider @Inject constructor(
    private val encryptionConfig: EncryptionConfig
) {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(encryptionConfig.algorithm).apply {
            init(
                KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setKeySize(encryptionConfig.keySize * 8)
                    .setBlockModes(encryptionConfig.blockMode)
                    .setEncryptionPaddings(encryptionConfig.padding)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    companion object {
        const val ALIAS = "master"
    }
}
