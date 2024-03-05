package com.app.contextualdemo.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import com.app.contextualdemo.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.contextualdemo.extension.getAppString
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens

@Composable
fun SettingsScreen() {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            textAlign = TextAlign.Center,
            text = getAppString(resId = R.string.title_settings),
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navigator.navigate(Screens.AppKeyValidation.route) {
                    popUpTo(Screens.Settings.route) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(text = getAppString(resId = R.string.change_app_key))
        }
    }
}