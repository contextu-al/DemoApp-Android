
package com.app.contextualdemo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.contextualdemo.ui.screen.guideblock.GuideBlockRoute

object GuideBlockDestination : AppNavigationDestination {
    override val route = "guide_block_route"
    override val destination = "guide_block_destination"
}

fun NavGraphBuilder.screenGuideBlock(
    onNavigateToDetailsDestination: (AppNavigationDestination, String) -> Unit
) = composable(route = GuideBlockDestination.route) {
    GuideBlockRoute { destination, data ->
        onNavigateToDetailsDestination(destination, data)
    }
}
