
package com.app.contextualdemo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.contextualdemo.ui.screen.screenone.ScreenOneRoute

object ScreenOneDestination : AppNavigationDestination {
    override val route = "screen_one_route"
    override val destination = "screen_one_destination"
}

fun NavGraphBuilder.screenOneGraph(
    onNavigateToDetailsDestination: (AppNavigationDestination, String) -> Unit
) = composable(route = ScreenOneDestination.route) {
    ScreenOneRoute { destination, data ->
        onNavigateToDetailsDestination(destination, data)
    }
}
