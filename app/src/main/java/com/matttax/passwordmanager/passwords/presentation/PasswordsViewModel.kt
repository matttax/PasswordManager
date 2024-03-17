package com.matttax.passwordmanager.passwords.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matttax.passwordmanager.passwords.data.PasswordsRepository
import com.matttax.passwordmanager.passwords.domain.PasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(
    private val passwordsRepository: PasswordsRepository
): ViewModel() {

    private val _passwords = MutableStateFlow<List<PasswordData>>(emptyList())
    val passwords = _passwords.asStateFlow()

    init {
        passwordsRepository.getAll()
            .onEach {
                _passwords.value = it.list
            }.launchIn(viewModelScope)
    }

    fun savePassword(passwordData: PasswordData, icon: Bitmap?) {
        viewModelScope.launch {
            passwordsRepository.addPassword(passwordData, icon)
        }
    }

    fun removePasswordById(id: String) {
        viewModelScope.launch {
            passwordsRepository.removePassword(id)
        }
    }
}
