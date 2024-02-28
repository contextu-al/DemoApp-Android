package com.app.contextualdemo.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.contextualdemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    startDestination: TopLevelDestination = TopLevelDestination.ScreenOne
) = remember(
    snackbarHostState,
    coroutineScope,
    navController,
    startDestination
) {
    AppState(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope,
        navController = navController,
        startDestination = startDestination
    )
}

@Stable
class AppState(
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val startDestination: TopLevelDestination
) {
    init {
        coroutineScope.launch {
            snackbarMessages.collect { messages ->
                if (messages.isNotEmpty()) {
                    val message = messages.first()
                    snackbarHostState.showSnackbar(message = message)
                    snackbarMessages.update { messageList ->
                        messageList.filterNot { it == message }
                    }
                }
            }
        }
    }

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination
        @Composable get() {
            topLevelDestinations.firstOrNull { it.route == currentDestination?.route }
                ?.let { _currentTopLevelDestination = it }
            return _currentTopLevelDestination
        }

    val title: String
        @Composable get() {
            if (currentDestination?.route == currentTopLevelDestination.route)
                _title = LocalContext.current.getString(R.string.app_name)
            else
                _title = LocalContext.current.getString(R.string.app_name)

            return _title
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route == currentTopLevelDestination.route

    /**
     * Top level destinations to be used in the BottomBar.
     */
    val topLevelDestinations = TopLevelDestination.values()
    private var _title by mutableStateOf("Watchers")

    private var _currentTopLevelDestination by mutableStateOf(startDestination)
    private var _currentDrawerLevelDestination by mutableStateOf(null)

    private val snackbarMessages = MutableStateFlow<List<String>>(emptyList())

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination The [CinemaxNavigationDestination] the app needs to navigate to.
     * @param route Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: AppNavigationDestination, route: String? = null) =
        with(navController) {
            if (destination is TopLevelDestination) {
                navigate(route ?: destination.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items.
                    popUpTo(graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item.
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item.
                    restoreState = true
                }
            } else {
                navigate(route ?: destination.route)
            }
        }

    fun onBackClick() = navController.popBackStack()

    fun showMessage(message: String) = snackbarMessages.update { it + message }
}
