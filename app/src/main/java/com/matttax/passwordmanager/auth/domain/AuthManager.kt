package com.matttax.passwordmanager.auth.domain

import com.matttax.passwordmanager.auth.data.MasterPasswordRepository
import com.matttax.passwordmanager.auth.presentation.PasswordCheckState
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val masterPasswordRepository: MasterPasswordRepository
) {

    val passwordExists: Boolean
        get() = masterPasswordRepository.passwordExists()

    fun checkPassword(inputPassword: String): PasswordCheckState {
        return masterPasswordRepository.extractPassword()?.let {
            return if (it.decodeToString() == inputPassword)
                PasswordCheckState.CORRECT
            else PasswordCheckState.INCORRECT
        } ?: PasswordCheckState.NO_PASSWORD
    }

    fun setNewPassword(inputPassword: String) {
        masterPasswordRepository.saveNewPassword(inputPassword)
    }
}
