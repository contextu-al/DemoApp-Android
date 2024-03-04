package com.app.contextualdemo.ui.screen.draganddrop

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun DraggableScreen(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize())
        {
            content()
        }
    }
}

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    viewModel: DragViewModel,
    content: @Composable (() -> Unit)
) {

    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    val context = LocalDensity.current
    var offsetX by remember {
        mutableFloatStateOf(currentState.dragOffset.x)
    }
    var offsetY by remember {
        mutableFloatStateOf(currentState.dragOffset.y)
    }

    Box(modifier = modifier
        .offset((offsetX / context.density).dp, (offsetY / context.density).dp)
        .onGloballyPositioned {
            currentPosition = it.localToWindow(Offset.Zero)
        }
        .pointerInput(Unit) {
            detectDragGestures(onDragStart = {
                viewModel.startDragging()
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.dragPosition = currentPosition + it
            }, onDrag = { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
                offsetY += dragAmount.y
                currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
            }, onDragEnd = {
                viewModel.stopDragging()
                currentState.isDragging = false
                if (currentState.isDragSuccess.not())
                    currentState.dragOffset = Offset.Zero
                else
                    currentState.dragOffset = Offset(offsetX, offsetY)
                if (currentState.isDragSuccess.not()) {
                    offsetX = 0f
                    offsetY = 0f
                }
            }, onDragCancel = {
                viewModel.stopDragging()
                currentState.dragOffset = Offset.Zero
                currentState.isDragging = false
                currentState.isDragSuccess = false
                if (currentState.isDragSuccess.not()) {
                    offsetX = 0f
                    offsetY = 0f
                    currentState.dragOffset = Offset.Zero
                }
            })
        }) {
        content()
    }
}

@Composable
fun <T> DropItem(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {

    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier
        .onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            val offset = dragPosition + dragOffset
            isCurrentDropTarget = (offset.y - rect.topRight.y) <= 100 && offset.y > 0 && offset.x > 0 && (offset.x - rect.topLeft.x) <= 150 && (offset.x - rect.topLeft.x) >= 50 && (offset.x - rect.topLeft.x) >= 0 && (offset.y - rect.topLeft.y) >= 0
            dragInfo.isDragSuccess = isCurrentDropTarget
        }
    }) {
        val data =
            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
        if (dragInfo.isDragging.not())
            content(isCurrentDropTarget, data)
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var isDragSuccess: Boolean by mutableStateOf(false)
    var dataToDrop by mutableStateOf<Any?>(null)
}