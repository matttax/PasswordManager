package com.matttax.passwordmanager.auth.data

import android.content.Context
import com.matttax.passwordmanager.encryption.Decrypter
import com.matttax.passwordmanager.encryption.Encryptor
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MasterPasswordRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val encryptor: Encryptor,
    private val decrypter: Decrypter
) {
    private val secretFile = File(context.filesDir, PASSWORD_FILE)

    fun passwordExists(): Boolean {
        return secretFile.exists() && secretFile.length() != 0L
    }

    fun extractPassword(): ByteArray? {
        if (!passwordExists()) {
            return null
        }
        return decrypter.decrypt(FileInputStream(secretFile))
    }

    fun saveNewPassword(newPassword: String) {
        val bytes = newPassword.encodeToByteArray()
        if (!secretFile.exists()) {
            secretFile.createNewFile()
        }
        val fos = FileOutputStream(secretFile)
        encryptor.encrypt(bytes, fos)
    }

    companion object {
        const val PASSWORD_FILE = "secret.txt"
    }
}
