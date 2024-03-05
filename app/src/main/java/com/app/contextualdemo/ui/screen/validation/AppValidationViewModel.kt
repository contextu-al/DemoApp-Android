package com.app.contextualdemo.ui.screen.validation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.contextualdemo.domain.usecase.AppKeyValidationUseCase
import com.app.contextualdemo.domain.model.AppKeyValidationModel
import com.app.contextualdemo.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppValidationViewModel @Inject constructor(
    private val appKeyValidationUseCase: AppKeyValidationUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UIState<AppKeyValidationModel>>(UIState.Error(""))
    val uiState = _uiState.asStateFlow()

    fun validateAppKey(appKey: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            appKeyValidationUseCase.invoke(appKey)
                .flowOn(Dispatchers.Main)
                .catch {
                    _uiState.value = UIState.Error(message = it.message ?: "")
                }
                .onEach {
                    if (it.feed.isNullOrEmpty().not())
                        _uiState.value = UIState.Response(it)
                    else
                        _uiState.value = UIState.Error("Invalid app key! Try again")
                }
                .collect()
        }
    }
}