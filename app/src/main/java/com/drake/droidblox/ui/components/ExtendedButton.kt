package com.drake.droidblox.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExtendedButton(
    title: String,
    subtitle: String,
    onClick: (() -> Unit) = {}
) {
    TitleWithSubtitle(
        title,
        subtitle,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    )
}