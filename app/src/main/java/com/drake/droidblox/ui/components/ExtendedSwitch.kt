package com.drake.droidblox.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drake.droidblox.ui.components.states.SwitchState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ExtendedSwitch(
    title: String,
    subtitle: String,
    enabled: Boolean = false,
    onClick: (Boolean) -> Unit = {},
    disabled: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleWithSubtitle(
            title = title,
            subtitle = subtitle,
            modifier = Modifier.weight(1f),
            disabled = disabled
        )
        Switch(
            checked = enabled,
            onCheckedChange = onClick,
            enabled = !disabled
        )
    }
}