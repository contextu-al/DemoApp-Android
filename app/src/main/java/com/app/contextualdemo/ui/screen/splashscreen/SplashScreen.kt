package com.app.contextualdemo.ui.screen.splashscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.contextualdemo.domain.model.AppKeyValidationModel
import com.app.contextualdemo.ui.UIState
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens
import com.app.contextualdemo.ui.screen.validation.AppValidationViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    appValidationViewModel: AppValidationViewModel = hiltViewModel()
) {
    val uiState = appValidationViewModel.uiState.collectAsStateWithLifecycle()
    val navigator = LocalNavigator.current
    val appKey = "Product_Hunt"
    LaunchedEffect(appKey) {
        appValidationViewModel.validateAppKey(appKey)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(visible = uiState.value is UIState.Loading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = uiState.value is UIState.Response) {
            (uiState.value as? UIState.Response<AppKeyValidationModel>).let {
                navigator.navigate(Screens.Main.route.replace("{appKey}", appKey)) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }

        AnimatedVisibility(visible = uiState.value is UIState.Error) {
            (uiState.value as? UIState.Error<AppKeyValidationModel>)?.let {
                Text(
                    text = it.message ?: "Error",
                    color = Color.Red
                )
            }
        }
    }
}