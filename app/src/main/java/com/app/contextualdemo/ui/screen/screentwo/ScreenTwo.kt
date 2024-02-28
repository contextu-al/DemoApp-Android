package com.app.contextualdemo.ui.screen.screentwo

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
internal fun ScreenTwoRoute(
    onClick: (AppNavigationDestination, String) -> Unit,
) {
    ScreenTwo()
}

@Composable
fun ScreenTwo() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Screen two",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}