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
        route = GuideBlockDestination.route,
        destination = GuideBlockDestination.destination,
        iconResourceId = R.drawable.ic_guide_block,
        title = "Guide Block"
    ),
    CodeBlockScreen(
        route = CodeBlockDestination.route,
        destination = CodeBlockDestination.destination,
        iconResourceId = R.drawable.ic_code,
        title = "Code Block"
    ),
}