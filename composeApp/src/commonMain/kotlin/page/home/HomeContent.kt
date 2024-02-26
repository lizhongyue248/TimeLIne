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
import model.Period
import store.AppStore
import store.GlobalStore
import store.Route
import toDateString

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

    val editDialog = remember { mutableStateOf(false) }
    val editName = remember { mutableStateOf("") }
    val editId = remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier.consumeWindowInsets(innerPadding)
            .nestedScroll(nestedScrollConnection),
        contentPadding = innerPadding
    ) {
        itemsIndexed(AppStore.state.periodList) { _, it ->
            DraggableItem(
                onDelete = {
                    GlobalStore.confirmDialog(
                        text = "Do you confirm remove ${it.name} ?",
                        onConfirm = {
                            AppStore.deletePeriodData(it.id, coroutineScope)
                        }
                    )
                },
                onEdit = {
                    editId.value = it.id
                    editName.value = it.name
                    editDialog.value = true
                },
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
    if (editDialog.value) {
        HomeActionDialog(
            editName.value,
            onDismissRequest = { editDialog.value = false },
            onConfirmation = { name ->
                AppStore.editPeriodData(editId.value, name, coroutineScope)
            })
    }
}

@Composable
fun HomeContentItem(item: Period) {
    Column(
        modifier = Modifier
            .clickable {
                GlobalStore.navigator.navigate(Route.detailPath(item.id, item.name))
            }
            .pointerHoverIcon(PointerIcon.Hand)
            .padding(8.dp, 4.dp, 8.dp, 0.dp),
    ) {
        Text(item.name, fontSize = 16.sp, maxLines = 1)
        Text(item.createDate.toDateString(), fontSize = 12.sp, lineHeight = 18.sp)
        HorizontalDivider(
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
    }
}
