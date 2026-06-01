package com.drake.droidblox.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TitleWithSubtitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    disabled: Boolean = false
) {
    Column(
        modifier = modifier
    ) {
        val color = if (!disabled) {
            Color.Unspecified
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 19.sp),
            color = color
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
            color = color
        )
    }
}