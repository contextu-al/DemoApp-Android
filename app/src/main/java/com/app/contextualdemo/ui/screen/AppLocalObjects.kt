package com.app.contextualdemo.ui.screen

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigator = staticCompositionLocalOf<NavHostController> {
    error("No object of Navigation")
}
