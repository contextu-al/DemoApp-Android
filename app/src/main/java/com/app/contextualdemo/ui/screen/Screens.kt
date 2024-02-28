package com.app.contextualdemo.ui.screen

sealed class Screens(val route : String) {
    data object Home : Screens("home_route/{appKey}")
    data object AppKeyValidation : Screens("app_key_route")
    data object Settings : Screens("settings_route")
}