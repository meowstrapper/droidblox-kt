package com.drake.droidblox.frontend.elements

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.drake.droidblox.backend.launchRoblox
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecentGamePlayed(
    gameName: String,
    gameCreator: String,
    gameIconUrl: String,
    playedAt: Long,
    leftAt: Long,
    robloxDeeplink: String
) {
    val currentActivity = LocalActivity.current as Activity

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gameIconUrl)
                    .build(),
                contentDescription = "droidblox",
                modifier = Modifier
                    .size(120.dp)
            )
            Column(
                modifier = Modifier.padding(3.dp)
            ) {
                Text(
                    gameName,
                    fontSize = 20.sp
                )

                val playedAtDate = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
                    .format(Date(playedAt))
                val leftAtDate = SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(Date(leftAt))

                Text(
                    "$gameCreator • $playedAtDate - $leftAtDate",
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    launchRoblox(
                        currentActivity,
                        robloxDeeplink
                    )
                }) {
                    Text("Rejoin")
                }
            }
        }
    }
}
