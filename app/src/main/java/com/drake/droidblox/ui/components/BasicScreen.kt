package com.drake.droidblox.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drake.droidblox.ui.view.navigation.NavigationDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicScreen(
    name: String,
    navController: NavController? = null,
    useLazyColumn: Boolean = false,
    navIcon: ImageVector = Icons.Default.Menu,
    navIconOnClick: (() -> Unit)? = null,
    lazyColumnContents: LazyListScope.() -> Unit = {},
    columnContents: @Composable (() -> Unit) = {}
) {
    NavigationDrawer(
        screenName = name,
        navController = navController
    ) { scope, drawerState ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(name) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (navIconOnClick != null) {
                                navIconOnClick()
                            } else {
                                scope.launch { drawerState.open() }
                            }
                        }) {
                            Icon(
                                navIcon,
                                ""
                            )
                        }
                    }
                )
            }
        ) { contentPadding ->
            if (useLazyColumn) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    content = lazyColumnContents
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(15.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) { columnContents() }
            }
        }
    }
}