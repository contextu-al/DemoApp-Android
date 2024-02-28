package com.app.contextualdemo.ui.navigation

import androidx.annotation.DrawableRes
import com.app.contextualdemo.R

enum class TopLevelDestination(
    override val route: String,
    override val destination: String,
    @DrawableRes val iconResourceId: Int,
    val title: String
) : AppNavigationDestination {
    ScreenOne(
        route = ScreenOneDestination.route,
        destination = ScreenOneDestination.destination,
        iconResourceId = R.drawable.ic_trending,
        title = "Screen 1"
    ),
    ScreenTwo(
        route = ScreenTwoDestination.route,
        destination = ScreenTwoDestination.destination,
        iconResourceId = R.drawable.ic_trending,
        title = "Screen 2"
    ),
}