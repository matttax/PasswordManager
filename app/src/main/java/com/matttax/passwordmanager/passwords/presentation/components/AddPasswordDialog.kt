package com.matttax.passwordmanager.passwords.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.matttax.passwordmanager.passwords.domain.PasswordData
import com.matttax.passwordmanager.passwords.icons.IconObserver
import com.matttax.passwordmanager.ui.common.LoginInputField
import com.matttax.passwordmanager.ui.common.PasswordInputField
import com.matttax.passwordmanager.ui.common.SearchField

@Composable
fun AddPasswordDialog(
    onAdd: (PasswordData, Bitmap?) -> Unit,
    onDismiss: () -> Unit
) {
    var url by rememberSaveable { mutableStateOf("https://google.com") }
    var login by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val iconObserver = remember { IconObserver(context.getDir("imageDir", Context.MODE_PRIVATE)) }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                        vertical = 5.dp
                    ),
                    text = "Add password",
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleMedium
                )
                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    onSearch = { url = it },
                    initialText = url
                )
                Spacer(modifier = Modifier.height(7.dp))
                LoginInputField(
                    modifier = Modifier.fillMaxWidth(),
                    onChange = { login = it }
                )
                PasswordInputField(
                    modifier = Modifier.fillMaxWidth(),
                    interactionSource = interactionSource,
                    onDone = { focusManager.clearFocus() },
                    onChange = { password = it }
                )
                AndroidView(
                    modifier = Modifier.weight(1f),
                    factory = {
                        WebView(it).apply {
                            webViewClient = WebViewClient()
                            webChromeClient = iconObserver
                        }
                    },
                    update = {
                        it.loadUrl(url)
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(Color.Transparent),
                    ) {
                        Text(
                            text = "Cancel",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Button(
                        onClick = {
                            onAdd(
                                PasswordData(
                                    webpageUrl = url,
                                    password = password,
                                    login = login,
                                    iconUri = iconObserver.path.absolutePath
                                ),
                                iconObserver.icon
                            )
                            onDismiss()
                        },
                        colors = ButtonDefaults.textButtonColors(Color.Transparent),
                    ) {
                        Text(
                            text = "Add",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
