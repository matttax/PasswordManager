package com.matttax.passwordmanager.passwords.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.matttax.passwordmanager.passwords.domain.PasswordData
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PasswordsScreen(
    passwordsFlow: StateFlow<List<PasswordData>>
) {
    val passwords by passwordsFlow.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        floatingActionButton = {
            AddNewButton {

            }
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(15.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
                    textAlign = TextAlign.Center,
                    text = "Passwords",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                items(
                    count = passwords.size,
                    key = { passwords[it].id }
                ) { index ->
                    PasswordItem(passwords[index])
                }
            }
        }
    }
}
