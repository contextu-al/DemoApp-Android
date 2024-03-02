package com.app.contextualdemo.ui.screen.draganddrop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.contextualdemo.domain.model.SquareBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DragViewModel @Inject constructor(): ViewModel() {

    var isDragging by mutableStateOf(false)
        private set

    var items by mutableStateOf(emptyList<SquareBlock>())
        private set

    var addedBlocks = mutableStateListOf<SquareBlock>()
        private set

    init {

        items = listOf(
            SquareBlock(),
            SquareBlock(),
            SquareBlock(),
            SquareBlock(true),
            SquareBlock(),
            SquareBlock(true),
            SquareBlock(true),
            SquareBlock(true),
            SquareBlock(),
            SquareBlock(true),
            SquareBlock(),
            SquareBlock(),
            SquareBlock(true),
            SquareBlock(true),
            SquareBlock(),
            SquareBlock(),
        )
    }

    fun startDragging() {
        isDragging = true
    }

    fun stopDragging() {
        isDragging = false
    }

}