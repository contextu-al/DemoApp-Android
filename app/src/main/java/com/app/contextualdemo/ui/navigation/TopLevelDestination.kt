package com.app.contextualdemo.ui.navigation

import androidx.annotation.DrawableRes
import com.app.contextualdemo.R

enum class TopLevelDestination(
    override val route: String,
    override val destination: String,
    @DrawableRes val iconResourceId: Int,
    val title: String
) : AppNavigationDestination {
    HomeScreen(
        route = HomeScreenDestination.route,
        destination = HomeScreenDestination.destination,
        iconResourceId = R.drawable.ic_home,
        title = "Home"
    ),
    GuideBlockScreen(
        route = ScreenTwoDestination.route,
        destination = ScreenTwoDestination.destination,
        iconResourceId = R.drawable.ic_trending,
        title = "Screen 2"
    ),
}