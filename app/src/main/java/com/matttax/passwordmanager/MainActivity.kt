package com.matttax.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matttax.passwordmanager.auth.biometric.BiometricAuthenticator
import com.matttax.passwordmanager.auth.presentation.AuthViewModel
import com.matttax.passwordmanager.auth.presentation.components.AuthScreen
import com.matttax.passwordmanager.passwords.presentation.components.PasswordsScreen
import com.matttax.passwordmanager.passwords.presentation.PasswordsViewModel
import com.matttax.passwordmanager.ui.theme.PasswordManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var biometricAuthenticator: BiometricAuthenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "auth"
                ) {
                    composable(
                        route = "auth"
                    ) {
                        val authViewModel = hiltViewModel<AuthViewModel>()
                        AuthScreen(authViewModel, biometricAuthenticator) {
                            navController.navigate("screen") {
                                popUpTo(0)
                            }
                        }
                    }
                    composable(
                        route = "screen"
                    ) {
                        val passwordsViewModel = hiltViewModel<PasswordsViewModel>()
                        PasswordsScreen(passwordsViewModel.passwords)
                    }
                }
            }
        }
    }
}
