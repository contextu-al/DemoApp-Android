package com.app.contextualdemo.domain.model

import androidx.compose.ui.geometry.Offset

data class SquareBlock(
    val isSelected: Boolean = false,
    var offset: Offset? = null,
    var animPosition: Int = -1,
    var text: String = ""
)
