package com.app.contextualdemo.ui.screen.draganddrop

import androidx.compose.runtime.getValue
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

    init {

        items = listOf(
            SquareBlock(),
            SquareBlock(),
            SquareBlock(),
            SquareBlock(true, animPosition = 6, text = "Aha!"),
            SquareBlock(),
            SquareBlock(true, animPosition = 3),
            SquareBlock(true, animPosition = 4),
            SquareBlock(true, animPosition = 5),
            SquareBlock(),
            SquareBlock(true, animPosition = 2),
            SquareBlock(),
            SquareBlock(),
            SquareBlock(true,  animPosition = 0),
            SquareBlock(true, animPosition = 1),
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