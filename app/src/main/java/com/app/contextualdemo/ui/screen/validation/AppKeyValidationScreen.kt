package com.app.contextualdemo.ui.screen.validation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.contextualdemo.R
import com.app.contextualdemo.domain.model.AppKeyValidationModel
import com.app.contextualdemo.extension.getAppString
import com.app.contextualdemo.ui.UIState
import com.app.contextualdemo.ui.screen.LocalNavigator
import com.app.contextualdemo.ui.screen.Screens

@Composable
fun AppKeyValidationScreen(
    modifier: Modifier = Modifier,
    appValidationViewModel: AppValidationViewModel = hiltViewModel(),
) {

    val appKey = remember {
        mutableStateOf("")
    }

    val navigator = LocalNavigator.current

    val uiState = appValidationViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = getAppString(resId = R.string.validation_title),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = getAppString(resId = R.string.validation_subTitle),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = appKey.value,
            onValueChange = {
                appKey.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = getAppString(resId = R.string.key_hint),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                appValidationViewModel.validateAppKey(appKey.value)
            }),
        )
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(visible = appKey.value.isEmpty().not()) {
            TextButton(
                onClick = {
                    appValidationViewModel.validateAppKey(appKey.value)
                }
            ) {
                Text(
                    text = getAppString(resId = R.string.text_continue),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        AnimatedVisibility(visible = uiState.value is UIState.Loading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = uiState.value is UIState.Response) {
            (uiState.value as? UIState.Response<AppKeyValidationModel>).let {
                if (appKey.value.isNotEmpty())
                    navigator.navigate(Screens.Home.route.replace("{appKey}", appKey.value))
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