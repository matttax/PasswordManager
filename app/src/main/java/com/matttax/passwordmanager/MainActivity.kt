package com.matttax.passwordmanager

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    startDestination = AUTH_ROUTE
                ) {
                    composable(
                        route = AUTH_ROUTE
                    ) {
                        val authViewModel = hiltViewModel<AuthViewModel>()
                        AuthScreen(authViewModel, biometricAuthenticator) {
                            // этот аргумент нужен на случай, если хранилище с мастер-паролем будет удалено,
                            // а хранилище с остальными паролями - нет
                            navController.singleInstanceNavigate("$PASSWORDS_ROUTE/$it")
                        }
                    }
                    composable(
                        route = "$PASSWORDS_ROUTE/{${PasswordsViewModel.IS_NEW_ARG}}",
                        arguments = listOf(
                            navArgument(PasswordsViewModel.IS_NEW_ARG) { type = NavType.BoolType }
                        )
                    ) {
                        val passwordsViewModel = hiltViewModel<PasswordsViewModel>()
                        PasswordsScreen(
                            passwordsViewModel.passwords,
                            passwordsViewModel::savePassword,
                            passwordsViewModel::removePasswordById
                        ) {
                            navController.singleInstanceNavigate(AUTH_ROUTE)
                        }
                    }
                }
            }
        }
    }

    private fun NavController.singleInstanceNavigate(route: String) {
        navigate(route) {
            popUpTo(0)
        }
    }

    companion object {
        const val AUTH_ROUTE = "auth"
        const val PASSWORDS_ROUTE = "passwords"
    }
}
