package com.app.contextualdemo.ui.screen.screenone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.contextualdemo.ui.navigation.AppNavigationDestination

@Composable
internal fun ScreenOneRoute(
    onClick: (AppNavigationDestination, String) -> Unit,
) {
    ScreenOne()
}

@Composable
fun ScreenOne() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Screen one",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}