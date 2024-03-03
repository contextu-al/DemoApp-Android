package com.app.contextualdemo.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun AppBottomNavigation(
    destinations: Array<TopLevelDestination>,
    currentDestination: TopLevelDestination,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {

    NavigationBar {
        destinations.forEach { item ->

            NavigationBarItem(
                icon = {
                     Icon(imageVector = ImageVector.vectorResource(id = item.iconResourceId), contentDescription = null)
                },
                selected = currentDestination == item,
                onClick = {
                    onNavigateToDestination.invoke(item)
                }
            )
        }
    }
}