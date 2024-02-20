package component.swipe


import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableItem(
    content: @Composable BoxScope.() -> Unit,
    onAnchoredStateChanged: (AnchoredDraggableState<DragAnchors>) -> Unit = {},
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {}
) {
    val density = LocalDensity.current

    val defaultActionSize = 80.dp
    val actionSizePx = with(density) { defaultActionSize.toPx() }
    val endActionSizePx = with(LocalDensity.current) { (80.dp * 2).toPx() }
    val state = remember {
        mutableStateOf(
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
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(IntrinsicSize.Min)
            .clip(RectangleShape)
    ) {

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
                            ((-state.value
                                .requireOffset()) + actionSizePx)
                                .roundToInt(), 0
                        )
                    }
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = onEdit,
            )
            DeleteAction(
                Modifier
                    .width(defaultActionSize)
                    .fillMaxHeight()
                    .offset {
                        IntOffset(
                            ((-state.value
                                .requireOffset() * 0.5f) + actionSizePx)
                                .roundToInt(), 0
                        )
                    }
                    .pointerHoverIcon(PointerIcon.Hand),
                onClick = onDelete
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .offset {
                    IntOffset(
                        x = -state.value
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state.value, Orientation.Horizontal, reverseDirection = true),
            content = content
        )
    }
    LaunchedEffect(state.value.targetValue) {
        onAnchoredStateChanged(state.value)
    }
}