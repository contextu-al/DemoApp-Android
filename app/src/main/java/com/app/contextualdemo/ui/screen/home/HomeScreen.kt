package com.app.contextualdemo.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    var pathOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val items = dragViewModel.items

    val offset by animateIntOffsetAsState(
        targetValue = if (moved) {
            IntOffset(pathOffset.x.toInt(), pathOffset.y.toInt())
        } else {
            IntOffset.Zero
        },
        //animationSpec = tween(durationMillis = 2500, delayMillis = 500, easing = LinearEasing),
        label = "offset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp)
            ) {

                itemsIndexed(items) { index, item ->
                    if (index == 7) {

                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(60.dp)
                        ) {

                            DropItem<SquareBlock>(
                                modifier = Modifier
                                    .size(60.dp)
                                    .onGloballyPositioned {
                                        if (moved)
                                            return@onGloballyPositioned
                                        val localOffset = Offset(
                                            it.boundsInRoot().left + 20,
                                            (it.boundsInRoot().top - it.boundsInParent().bottom)
                                        )
                                        item.offset = localOffset
                                    }

                            ) { isInbound, _ ->
                                if (isInbound && LocalDragTargetInfo.current.isDragSuccess) {
                                    moved = true
                                    val list = items.sortedBy {
                                        it.animPosition
                                    }.filter {
                                        it.offset != null && it.animPosition >= 0 && it.isSelected
                                    }

                                    LaunchedEffect(Unit) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            list.forEach {
                                                pathOffset = it.offset!!
                                                delay(700)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        ElevatedCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(60.dp)
                                .shadow(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(if (item.isSelected) Color.LightGray else Color.White)
                                    .onGloballyPositioned {
                                        if (moved)
                                            return@onGloballyPositioned
                                        val localOffset = Offset(
                                            it.boundsInRoot().left + 20,
                                            (it.boundsInRoot().top - it.boundsInParent().bottom)
                                        )
                                        item.offset = localOffset
                                        if (index == 12) {
                                            pathOffset = localOffset
                                        }
                                        if (index == items.size - 1)
                                            moved = true
                                    }
                            ) {
                                if (index == 3) {
                                    Text(
                                        text = item.text,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
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

        AnimatedVisibility(visible = moved) {
            Box(
                modifier = Modifier
                    .offset {
                        offset
                    }
                    .align(Alignment.Center)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                    contentDescription = null
                )
            }
        }
    }
}