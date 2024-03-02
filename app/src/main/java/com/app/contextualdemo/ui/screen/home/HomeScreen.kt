package com.app.contextualdemo.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.contextualdemo.R
import com.app.contextualdemo.domain.model.SquareBlock
import com.app.contextualdemo.ui.navigation.AppNavigationDestination
import com.app.contextualdemo.ui.screen.draganddrop.DragTarget
import com.app.contextualdemo.ui.screen.draganddrop.DragViewModel
import com.app.contextualdemo.ui.screen.draganddrop.DraggableScreen
import com.app.contextualdemo.ui.screen.draganddrop.DropItem
import com.app.contextualdemo.ui.screen.draganddrop.LocalDragTargetInfo
import kotlin.math.roundToInt

@Composable
internal fun HomeScreenRoute(
    onClick: (AppNavigationDestination, String) -> Unit,
) {
    DraggableScreen(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen(
    dragViewModel: DragViewModel = hiltViewModel()
) {

    var moved by remember { mutableStateOf(false) }
    val pxToMove = with(LocalDensity.current) {
        40.dp.toPx().roundToInt()
    }
    val offset by animateIntOffsetAsState(
        targetValue = if (moved) {
            IntOffset(pxToMove, Offset.Zero.y.toInt())
        } else {
            IntOffset.Zero
        },
        label = "offset"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp)
        ) {

            itemsIndexed(dragViewModel.items) { index, it ->
                if (index == 7) {

                    DropItem<SquareBlock>(
                        modifier = Modifier
                            .size(60.dp)
                    ) { isInbound, data ->
                        if (isInbound && LocalDragTargetInfo.current.isDragSuccess) {
                            Toast.makeText(LocalContext.current, "inbound", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(60.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(if (it.isSelected) Color.LightGray else Color.White)
                        ) {
                            if (index == 12 && it.isSelected) {
                                Box(
                                    modifier = Modifier.offset {
                                        offset
                                    }
                                        .align(Alignment.Center)
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                                        contentDescription = null
                                    )
                                    moved = !moved
                                }
                            }

                        }
                    }
                }
            }
        }

        DragTarget(
            dataToDrop = dragViewModel.items[7],
            viewModel = dragViewModel
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(8.dp)
                    .width(75.dp)
                    .height(60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
            }
        }

    }
}