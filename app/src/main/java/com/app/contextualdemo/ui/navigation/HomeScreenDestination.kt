
package com.app.contextualdemo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.contextualdemo.ui.screen.home.HomeScreenRoute

object HomeScreenDestination : AppNavigationDestination {
    override val route = "home_screen_route"
    override val destination = "home_screen_destination"
}

fun NavGraphBuilder.homeScreenGraph(
    onNavigateToDetailsDestination: (AppNavigationDestination, String) -> Unit
) = composable(route = HomeScreenDestination.route) {
    HomeScreenRoute { destination, data ->
        onNavigateToDetailsDestination(destination, data)
    }
}
