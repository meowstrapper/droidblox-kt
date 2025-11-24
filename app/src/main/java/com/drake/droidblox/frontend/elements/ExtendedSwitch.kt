package com.drake.droidblox.frontend.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ExtendedSwitch(
    title: String,
    subtitle: String,
    enabled: Boolean = false,
    onClick: ((value: Boolean) -> Unit) = {}
) {
    var toggled by remember { mutableStateOf(enabled) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TitleWithSubtitle(title, subtitle)
        }
        Switch(
            checked = toggled,
            onCheckedChange = {
                toggled = it
                onClick(it)
            }
        )
    }
}