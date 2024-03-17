package com.matttax.passwordmanager.passwords.data

import android.content.Context
import android.graphics.Bitmap
import androidx.datastore.dataStore
import com.matttax.passwordmanager.passwords.domain.PasswordData
import com.matttax.passwordmanager.passwords.domain.Passwords
import com.matttax.passwordmanager.passwords.icons.IconStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val iconStorage: IconStorage,
    passwordSerializer: PasswordSerializer
) {
    private val Context.dataStore by dataStore(
        fileName = "passwords.json",
        serializer = passwordSerializer
    )

    fun getAll(): Flow<Passwords> {
        return context.dataStore.data
    }

    suspend fun addPassword(passwordData: PasswordData, icon: Bitmap?) {
        context.dataStore.updateData {
            it.copy(list = listOf(passwordData) + it.list)
        }
        icon?.let { iconBitmap ->
            passwordData.iconUri?.let { uri ->
                iconStorage.saveToInternalStorage(uri, iconBitmap)
            }
        }
    }

    suspend fun removePassword(id: String) {
        context.dataStore.updateData {
            it.list.forEach { password ->
                if (password.id == id && password.iconUri != null) {
                    iconStorage.remove(password.iconUri)
                }
            }
            it.copy(
                list = it.list.toMutableList().filterNot { password -> password.id == id }
            )
        }
    }
}
