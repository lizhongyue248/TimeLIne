package page.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.swipe.DragAnchors
import component.swipe.DraggableItem
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import model.TimeData
import model.TimeDataList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(innerPadding: PaddingValues) {

    val coroutineScope = rememberCoroutineScope()

    var currentSwipeState: AnchoredDraggableState<DragAnchors>? by remember {
        mutableStateOf(null)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            /**
             * we need to intercept the scroll event and check whether there is an open box
             * if so ,then we need to swipe that box back and reset the state
             */
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (currentSwipeState != null && currentSwipeState!!.currentValue != DragAnchors.Center) {
                    coroutineScope.launch {
                        currentSwipeState!!.animateTo(DragAnchors.Center)
                        currentSwipeState = null
                    }
                }
                return Offset.Zero
            }
        }
    }

    LazyColumn(
        modifier = Modifier.consumeWindowInsets(innerPadding)
            .nestedScroll(nestedScrollConnection),
        contentPadding = innerPadding
    ) {
        itemsIndexed(TimeDataList) { _, it ->
            DraggableItem(
                onAnchoredStateChanged = {
                    if (it.targetValue == DragAnchors.Center && currentSwipeState == it) {
                        currentSwipeState = null
                        return@DraggableItem
                    }
                    if (currentSwipeState == null) {
                        currentSwipeState = it
                    } else {
                        coroutineScope.launch {
                            currentSwipeState!!.animateTo(DragAnchors.Center)
                            currentSwipeState = it
                        }
                    }
                },
                content = {
                    HomeContentItem(it)
                }
            )
        }
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
        HorizontalDivider(color = Color.LightGray, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
    }
}

private fun LocalDateTime.toDateString(): String {
    return "${this.year}-${this.monthNumber}-${this.dayOfMonth}"
}
