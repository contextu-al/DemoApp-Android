package com.app.contextualdemo.ui.screen.guideblock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.app.contextualdemo.ui.UIState
import com.app.contextualdemo.ui.screen.GuideBlock
import com.contextu.al.Contextual
import com.contextu.al.data.storage.room.Tags
import com.contextu.al.model.customguide.ContextualContainer
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
class GuideBlockViewModel @Inject constructor(): ViewModel() {

    private val _guideBlockState = MutableStateFlow<UIState<ContextualContainer>>(UIState.Error(""))
    val guideBlockState = _guideBlockState.asStateFlow()

    private val _tagsState = MutableStateFlow<UIState<Tags?>>(UIState.Error(""))
    val tagsState = _tagsState.asStateFlow()

    fun registerGuideBlock(guideBlockKey: GuideBlock) {
        viewModelScope.launch {

            Contextual.registerGuideBlock(guideBlockKey.name)
                .asFlow()
                .flowOn(Dispatchers.Main)
                .catch {
                    _guideBlockState.value = UIState.Error(message = it.message ?: "")
                }
                .onEach {
                    _guideBlockState.value = UIState.Response(it)
                }
                .collect()
        }
    }

    fun tagNumber(key: String, number: Number) {
        viewModelScope.launch {
            Contextual.tagNumeric(key, number)
                .flowOn(Dispatchers.Main)
                .catch {

                }
                .onEach {
                    _tagsState.value = UIState.Response(it)
                }
                .collect()
        }

    }
}