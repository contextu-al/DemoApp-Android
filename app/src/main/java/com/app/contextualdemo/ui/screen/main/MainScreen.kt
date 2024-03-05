package com.app.contextualdemo.ui.screen.main

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.app.contextualdemo.R
import com.app.contextualdemo.ui.navigation.AppBottomNavigation
import com.app.contextualdemo.ui.navigation.AppNavHost
import com.app.contextualdemo.ui.navigation.AppState
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens
import com.contextu.al.Contextual
import com.contextu.al.core.CtxEventObserver
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    appState: AppState,
    appKey: String
) {
    val navigator = LocalNavigator.current
    initContextualSdk(appKey, LocalContext.current.applicationContext as Application)
    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                destinations = appState.topLevelDestinations,
                currentDestination = appState.currentTopLevelDestination,
                onNavigateToDestination = appState::navigate,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .consumeWindowInsets(paddingValues = innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "App key: $appKey",
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.baseline_settings_24
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navigator.navigate(Screens.Settings.route)
                    }
                )
            }

            AppNavHost(
                modifier = Modifier,
                navController = appState.navController,
                startDestination = appState.startDestination,
                onNavigateToDestination = appState::navigate,
                onBackClick = appState::onBackClick,
                onShowMessage = { message -> appState.showMessage(message) },
            )
        }
    }
}


private fun initContextualSdk(
    appKey: String,
    application: Application
) {
    Contextual.init(application, appKey, object : CtxEventObserver {
        override fun onInstallRegistered(installId: UUID, context: Context) {
            Toast.makeText(application, "Contextual sdk initiated", Toast.LENGTH_LONG).show()
        }

        override fun onInstallRegisterError(errorMsg: String) {
        }
    })
}