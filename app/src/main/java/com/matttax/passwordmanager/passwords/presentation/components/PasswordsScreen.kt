package com.matttax.passwordmanager.passwords.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.matttax.passwordmanager.passwords.domain.PasswordData
import com.matttax.passwordmanager.passwords.presentation.state.DialogState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PasswordsScreen(
    passwordsFlow: StateFlow<List<PasswordData>>,
    onAdd: (PasswordData, Bitmap?) -> Unit,
    onRemove: (String) -> Unit,
    onStop: () -> Unit
) {
    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    if (lifecycle == Lifecycle.Event.ON_STOP) {
        onStop()
    }
    val passwords by passwordsFlow.collectAsState()
    var dialogState by remember { mutableStateOf<DialogState?>(null) }
    val clipboardManager = LocalClipboardManager.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        floatingActionButton = {
            AddNewButton { dialogState = DialogState.Add }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
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
                    PasswordItem(
                        passwordData = passwords[index],
                        onCopyClick = { password ->
                            password?.let { clipboardManager.setText(AnnotatedString(it)) }
                        },
                        onRemoveClick = { dialogState = DialogState.Remove(it) }
                    )
                }
            }
        }
    }
    when (val state = dialogState) {
        is DialogState.Add -> {
            AddPasswordDialog(
                onAdd = { data, icon -> onAdd(data, icon) },
                onDismiss = { dialogState = null }
            )
        }
        is DialogState.Remove -> {
            YesNoDialog(
                title = "Delete?",
                yesText = "Yes",
                onYes = { onRemove(state.id) },
                onDismiss = { dialogState = null },
                content = { Spacer(Modifier.height(15.dp)) }
            )
        }
        else -> { }
    }
}
