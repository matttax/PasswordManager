package com.matttax.passwordmanager.encryption

import com.matttax.passwordmanager.encryption.cipher.DecryptCipherProvider
import com.matttax.passwordmanager.encryption.config.EncryptionConfig
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Decrypter @Inject constructor(
    private val decryptCipherProvider: DecryptCipherProvider,
    private val encryptionConfig: EncryptionConfig
) {

    fun decryptToString(inputStream: InputStream): String {
        return decrypt(inputStream).decodeToString()
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val iv = ByteArray(encryptionConfig.keySize)
            it.read(iv)
            val cipher = decryptCipherProvider.getDecryptCipher(iv)
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(encryptionConfig.chunkSize)
            while (inputStream.available() > encryptionConfig.chunkSize) {
                inputStream.read(buffer)
                val ciphertextChunk = cipher.update(buffer)
                outputStream.write(ciphertextChunk)
            }
            val remainingBytes = inputStream.readBytes()
            val lastChunk = cipher.doFinal(remainingBytes)
            outputStream.write(lastChunk)
            outputStream.toByteArray()
        }
    }
}
