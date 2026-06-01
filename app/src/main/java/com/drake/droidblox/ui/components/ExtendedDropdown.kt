package com.drake.droidblox.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.drake.droidblox.ui.components.states.DropdownState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtendedDropdown(
    title: String,
    subtitle: String,
    default: String,
    items: List<String>,
    onChoose: (String) -> Unit = {},
    disabled: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

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
        // https://stackoverflow.com/a/67111599
        ExposedDropdownMenuBox(
            expanded = expanded && !disabled,
            onExpandedChange = { if (!disabled) expanded = !expanded },
            modifier = Modifier.weight(.5f)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth(),
                singleLine = true,
                maxLines = 1,
                value = default,
                onValueChange = {},
                readOnly = true,
                enabled = !disabled,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { if (!disabled) expanded = false }
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            if (!disabled) {
                                onChoose(it)
                                expanded = false
                            }
                        }
                    )
                }
            }
        }
    }
}