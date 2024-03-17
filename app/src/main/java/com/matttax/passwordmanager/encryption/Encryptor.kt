package com.matttax.passwordmanager.encryption

import com.matttax.passwordmanager.encryption.cipher.EncryptCipherProvider
import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Encryptor @Inject constructor(
    private val encryptionCipherProvider: EncryptCipherProvider,
    private val encryptionConfig: EncryptionConfig
) {
    fun encrypt(bytes: ByteArray, outputStream: OutputStream) {
        val cipher = encryptionCipherProvider.encryptCipher
        val iv = cipher.iv
        outputStream.use {
            it.write(iv)
            val inputStream = ByteArrayInputStream(bytes)
            val buffer = ByteArray(encryptionConfig.chunkSize)
            while (inputStream.available() > encryptionConfig.chunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                it.write(ciphertextChunk)
            }
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            it.write(lastChunk)
        }
    }
}
