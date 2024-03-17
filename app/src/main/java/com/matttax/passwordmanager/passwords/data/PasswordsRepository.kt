package com.matttax.passwordmanager.passwords.data

import android.content.Context
import androidx.datastore.dataStore
import com.matttax.passwordmanager.passwords.domain.PasswordData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasswordsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    passwordSerializer: PasswordSerializer
) {
    private val Context.dataStore by dataStore(
        fileName = "passwords.json",
        serializer = passwordSerializer
    )

    fun getAll(): Flow<PasswordData> {
        return context.dataStore.data
    }

    suspend fun addPassword(passwordData: PasswordData) {
        context.dataStore.updateData {
            passwordData
        }
    }
}