package com.matttax.passwordmanager.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = MildGreen,
    primaryContainer = SuperDarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.LightGray,
    onPrimaryContainer = MildGreen
)

private val LightColorPalette = lightColorScheme(
    primary = MildGreen,
    primaryContainer = SuperLightGray,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Gray,
    onPrimaryContainer = MildGreen
)

@Composable
fun PasswordManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}