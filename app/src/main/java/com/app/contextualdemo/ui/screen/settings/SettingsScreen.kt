package com.app.contextualdemo.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import com.app.contextualdemo.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.contextualdemo.extension.getAppString
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens

@Composable
fun SettingsScreen() {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
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