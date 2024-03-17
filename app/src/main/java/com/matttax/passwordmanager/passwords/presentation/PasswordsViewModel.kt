package com.matttax.passwordmanager.passwords.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matttax.passwordmanager.passwords.data.PasswordsRepository
import com.matttax.passwordmanager.passwords.domain.PasswordData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(
    private val passwordsRepository: PasswordsRepository
): ViewModel() {

    private val _passwords = MutableStateFlow<List<PasswordData>>(emptyList())
    val passwords = _passwords.asStateFlow()

    init {
        passwordsRepository.getAll()
            .onEach { item ->
                _passwords.update {
                    it.toMutableList().apply { add(item) }
                }
            }.launchIn(viewModelScope)
    }

}
