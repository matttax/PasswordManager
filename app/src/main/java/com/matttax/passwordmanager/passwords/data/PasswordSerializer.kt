package com.matttax.passwordmanager.passwords.data

import androidx.datastore.core.Serializer
import com.matttax.passwordmanager.encryption.Decrypter
import com.matttax.passwordmanager.encryption.Encryptor
import com.matttax.passwordmanager.passwords.domain.PasswordData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class PasswordSerializer @Inject constructor(
    private val encryptor: Encryptor,
    private val decrypter: Decrypter
) : Serializer<PasswordData> {

    override val defaultValue: PasswordData
        get() = PasswordData(id = "0")

    override suspend fun readFrom(input: InputStream): PasswordData {
        val decryptedBytes = decrypter.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = PasswordData.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: PasswordData, output: OutputStream) {
        encryptor.encrypt(
            bytes = Json.encodeToString(
                serializer = PasswordData.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}
