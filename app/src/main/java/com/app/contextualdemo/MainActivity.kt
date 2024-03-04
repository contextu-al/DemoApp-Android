package com.app.contextualdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.contextualdemo.ui.navigation.rememberAppState
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens
import com.app.contextualdemo.ui.screen.main.MainScreen
import com.app.contextualdemo.ui.screen.settings.SettingsScreen
import com.app.contextualdemo.ui.screen.splashscreen.SplashScreen
import com.app.contextualdemo.ui.screen.validation.AppKeyValidationScreen
import com.app.contextualdemo.ui.theme.ContextualTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ContextualTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { paddingValues ->

                    CompositionLocalProvider(
                        LocalNavigator provides rememberNavController()
                    ) {
                        NavHost(
                            navController = LocalNavigator.current,
                            startDestination = Screens.Splash.route,
                            modifier = Modifier.padding(paddingValues = paddingValues)
                        ) {
                            composable(
                                Screens.Main.route,
                                arguments = listOf(navArgument("appKey") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                MainScreen(
                                    rememberAppState(),
                                    backStackEntry.arguments?.getString("appKey") ?: ""
                                )
                            }
                            composable(Screens.AppKeyValidation.route) {
                                AppKeyValidationScreen()
                            }
                            composable(Screens.Settings.route) {
                                SettingsScreen()
                            }
                            composable(Screens.Splash.route) {
                                SplashScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}