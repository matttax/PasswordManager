package com.matttax.passwordmanager.passwords.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.matttax.passwordmanager.passwords.domain.PasswordData

@Composable
fun PasswordItem(
    passwordData: PasswordData,
    onCopyClick: (password: String?) -> Unit,
    onRemoveClick: (id: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(0.3f)
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
        ) {
            AsyncImage(
                model = passwordData.iconUri,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = passwordData.login.toString(),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        IconButton(
            onClick = { onCopyClick(passwordData.password) }
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { onRemoveClick(passwordData.id) }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}
