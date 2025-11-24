package com.drake.droidblox.frontend.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.drake.droidblox.frontend.elements.BasicScreen

@Composable
fun AboutScreen(
    navController: NavController? = null
) {
    BasicScreen("About", navController) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://github.com/meowstrapper/DroidBlox/blob/main/assets/icon.png?raw=true")
                    .build(),
                contentDescription = "droidblox",
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                // DroidBlox is a bootstrapper for Roblox's android client that gives you additional features.
                // It is a Bloxstrap alternative to android except currently possible in the android version

                // License
                // GitHub Repository
                // Discord Server
                // Credits
                // Licenses
                append("DroidBlox is a bootstrapper for Roblox's android client that gives you additional features.\n\nIt is a ")
                withLink(
                    LinkAnnotation.Url(
                        url = "https://github.com/bloxstraplabs/bloxstrap",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("Bloxstrap")
                }
                append(" alternative to android except there are some features that are ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(" not currently possible in the android version.\n\n")
                }

                withLink(
                    LinkAnnotation.Url(
                        url = "https://github.com/meowstrapper/DroidBlox/blob/main/LICENSE",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("License")
                }

                append("\n")

                withLink(
                    LinkAnnotation.Url(
                        url = "https://github.com/meowstrapper/DroidBlox",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("GitHub Repository")
                }

                append("\n")

                withLink(
                    LinkAnnotation.Url(
                        url = "https://discord.gg/kVmH76umHv",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("Discord Server")
                }

                append("\n")

                withLink(
                    LinkAnnotation.Url(
                        url = "https://github.com/meowstrapper/DroidBlox?tab=readme-ov-file#credits",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("Credits")
                }

                append("\n")

                withLink(
                    LinkAnnotation.Url(
                        url = "https://github.com/meowstrapper/DroidBlox?tab=readme-ov-file#licenses-libraries-used-and-codes-that-i-refactored-or-used",
                        styles = TextLinkStyles(style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ))
                    )
                ) {
                    append("Licenses")
                }
            })

        }
    }
}

@Preview
@Composable
private fun Preview() {
    AboutScreen()
}