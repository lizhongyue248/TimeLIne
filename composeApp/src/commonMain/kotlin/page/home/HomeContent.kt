package page.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.swipe.DeleteAction
import component.swipe.DragAnchors
import component.swipe.DraggableItem
import component.swipe.EditAction
import kotlinx.datetime.LocalDateTime
import model.TimeData
import model.TimeDataList
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(innerPadding: PaddingValues) {
    val density = LocalDensity.current

    val defaultActionSize = 80.dp
    val actionSizePx = with(density) { defaultActionSize.toPx() }
    val endActionSizePx = with(density) { (defaultActionSize * 2).toPx() }

    val draggableStateList = remember {
        TimeDataList.map {
            AnchoredDraggableState(
                initialValue = DragAnchors.Center,
                anchors = DraggableAnchors {
                    DragAnchors.Center at 0f
                    DragAnchors.End at endActionSizePx
                },
                positionalThreshold = { distance: Float -> distance * 0.5f },
                velocityThreshold = { with(density) { 100.dp.toPx() } },
                animationSpec = tween(),
            )
        }.let { mutableStateOf(it) }
    }

    LazyColumn(
        modifier = Modifier.consumeWindowInsets(innerPadding),
        contentPadding = innerPadding
    ) {
        itemsIndexed(TimeDataList) { index, it ->
            val state = draggableStateList.value[index]
            DraggableItem(state = state,
                endAction = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd),
                    ) {
                        EditAction(
                            Modifier
                                .width(defaultActionSize)
                                .fillMaxHeight()
                                .offset {
                                    IntOffset(
                                        ((-state
                                            .requireOffset()) + actionSizePx)
                                            .roundToInt(), 0
                                    )
                                }
                        )
                        DeleteAction(
                            Modifier
                                .width(defaultActionSize)
                                .fillMaxHeight()
                                .offset {
                                    IntOffset(
                                        ((-state
                                            .requireOffset() * 0.5f) + actionSizePx)
                                            .roundToInt(), 0
                                    )
                                }
                        )
                    }
                }, content = {
                    HomeContentItem(it)
                })
        }
    }
    LaunchedEffect(draggableStateList) {
        println(draggableStateList.value)
    }
}

@Composable
fun HomeContentItem(item: TimeData) {
    Column(
        modifier = Modifier
            .clickable { }
            .pointerHoverIcon(PointerIcon.Hand)
            .padding(8.dp, 4.dp, 8.dp, 0.dp),
    ) {
        Text(item.name, fontSize = 16.sp, maxLines = 1)
        Text(item.createDate.toDateString(), fontSize = 12.sp, lineHeight = 18.sp)
        HorizontalDivider(color = Color.LightGray, modifier = Modifier.fillMaxWidth())
    }
}

private fun LocalDateTime.toDateString(): String {
    return "${this.year}-${this.monthNumber}-${this.dayOfMonth}"
}
