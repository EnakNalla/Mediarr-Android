package com.github.enaknalla.mediarrtv.ui.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

// use dracula colors ins Colors.kt
val colorScheme = darkColorScheme(
    primary = DraculaPurple,
    secondary = DraculaPink,
    tertiary = DraculaCyan,
    onPrimary = DraculaForeground,
    onSecondary = DraculaForeground,
    onTertiary = DraculaForeground,
    surface = DraculaCurrentLine,
    onSurface = DraculaForeground,
    background = DraculaBackground,
    onBackground = DraculaForeground,
    error = DraculaRed,
    onError = DraculaForeground,
)

@Composable
fun MediarrTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
