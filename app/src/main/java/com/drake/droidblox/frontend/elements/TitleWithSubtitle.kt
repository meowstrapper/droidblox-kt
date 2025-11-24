package com.drake.droidblox.frontend.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TitleWithSubtitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(title, fontSize = 19.sp)
        Text(subtitle, fontSize = 13.sp)
    }
}