package com.github.enaknalla.mediarrtv.ui.components

import androidx.compose.runtime.Composable
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.MaterialTheme
import com.github.enaknalla.mediarrtv.ui.theme.DraculaComment

@Composable
fun Btn(onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick,
        shape = ButtonDefaults.shape(
            shape = MaterialTheme.shapes.medium
        ),
        colors = ButtonDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = DraculaComment,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        content()
    }
}