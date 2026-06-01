/*
 * TODO: Update this UI to use spaces instead of filling the whole height
 */
package com.drake.droidblox.ui.view.views.playlogs.models

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import com.drake.droidblox.ui.components.BasicScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Fix this nasty ass gui
@Composable
fun RecentGamePlayed(
    gameName: String,
    gameCreator: String,
    gameIconUrl: String,
    playedAt: String,
    leftAt: String,
    onRejoinClick: () -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = gameIconUrl,
                contentDescription = "Game Icon",
                modifier = Modifier.fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = gameName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    )

                    Text(
                        text = "$gameCreator • $playedAt - $leftAt",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Button(
                    onClick = onRejoinClick,
                    modifier = Modifier.align(Alignment.End),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text("Rejoin")
                }
            }
        }
    }
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(120.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(gameIconUrl)
//                    .build(),
//                contentDescription = "droidblox",
//                modifier = Modifier
//                    .size(120.dp)
//            )
//            Column(
//
//                modifier = Modifier.padding(3.dp)
//            ) {
//                Text(
//                    gameName,
//                    fontSize = 20.sp
//                )
//
//                val playedAtDate = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
//                    .format(Date(playedAt))
//                val leftAtDate = SimpleDateFormat("HH:mm", Locale.getDefault())
//                    .format(Date(leftAt))
//
//                Text(
//                    "$gameCreator • $playedAtDate - $leftAtDate",
//                    fontSize = 13.sp
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Button(onClick = onRejoinClick) {
//                    Text("Rejoin")
//                }
//            }
//        }
//    }
}

@Preview
@Composable
private fun RecentGamePlayedPreview() {
    BasicScreen("meowers") {
        RecentGamePlayed(
            gameName = "Murder Mystery 2",
            gameCreator = "Nikilis",
            gameIconUrl = "",
            playedAt = "bro idk",
            leftAt = "idk too"
        )
    }
}

//
//@Preview
//@Composable
//private fun TestPreview() {
//    BasicScreen("Test") {
//        RecentGamePlayed(
//            "Murder Mystery 2",
//            "Nikilis",
//            "https://avatars.githubusercontent.com/u/124619531?v=4",
//            System.currentTimeMillis() - 10000,
//            System.currentTimeMillis(),
//            "roblox://experiences/start?placeId=142823291"
//        )
//    }
//}