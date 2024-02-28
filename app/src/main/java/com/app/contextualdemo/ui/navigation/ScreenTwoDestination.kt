
package com.app.contextualdemo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.contextualdemo.ui.screen.screenone.ScreenOneRoute
import com.app.contextualdemo.ui.screen.screentwo.ScreenTwoRoute

object ScreenTwoDestination : AppNavigationDestination {
    override val route = "screen_two_route"
    override val destination = "screen_two_destination"
}

fun NavGraphBuilder.screenTwoGraph(
    onNavigateToDetailsDestination: (AppNavigationDestination, String) -> Unit
) = composable(route = ScreenTwoDestination.route) {
    ScreenTwoRoute { destination, data ->
        onNavigateToDetailsDestination(destination, data)
    }
}
