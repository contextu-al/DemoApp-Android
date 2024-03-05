package com.app.contextualdemo.ui.screen

sealed class Screens(val route : String) {
    data object Main : Screens("main_route/{appKey}")
    data object AppKeyValidation : Screens("app_key_route")
    data object Settings : Screens("settings_route")
    data object Splash : Screens("splash_route")
}