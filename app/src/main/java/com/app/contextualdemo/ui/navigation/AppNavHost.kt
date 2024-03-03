package com.app.contextualdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: AppNavigationDestination,
    onNavigateToDestination: (AppNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        homeScreenGraph(onNavigateToDestination)
        screenGuideBlock(onNavigateToDestination)
        screenCodeBlock(onNavigateToDestination)
    }
}
