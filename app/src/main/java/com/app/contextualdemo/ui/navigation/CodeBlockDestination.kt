
package com.app.contextualdemo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.contextualdemo.ui.screen.codeblock.CodeBlockRoute

object CodeBlockDestination : AppNavigationDestination {
    override val route = "code_block_route"
    override val destination = "code_block_destination"
}

fun NavGraphBuilder.screenCodeBlock(
    onNavigateToDetailsDestination: (AppNavigationDestination, String) -> Unit
) = composable(route = CodeBlockDestination.route) {
    CodeBlockRoute { destination, data ->
        onNavigateToDetailsDestination(destination, data)
    }
}
