package page.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.NoDataPage
import model.Event
import model.EventType
import store.AppStore
import store.GlobalStore
import store.Route

@Composable
fun DetailContent(timeId: String, innerPadding: PaddingValues) {
    val data = AppStore.state.periodEvent[timeId] ?: emptyMap()
    if (data.isEmpty()) {
        NoDataPage()
        return
    }

    var columnHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    LazyColumn(
        modifier = Modifier.consumeWindowInsets(innerPadding).padding(top = 8.dp)
            .onGloballyPositioned {
                columnHeight = with(density) {
                    it.size.height.toDp()
                }
            },
        contentPadding = innerPadding
    ) {
        itemsIndexed(data.keys.sortedDescending()) { index, key ->
            val list = data[key]!!
            Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    "$key",
                    modifier = Modifier.padding(end = 0.dp, top = if (index == 0) 0.dp else 18.dp)
                        .width(60.dp)
                )
                Column(
                    modifier = Modifier
                        .drawWithContent {
                            drawContent()
                            if (index == 0) {
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, columnHeight.toPx()),
                                    strokeWidth = 1.dp.toPx() // Width of the border
                                )
                            }
                        }
                ) {
                    list.mapIndexed { lineDataIndex, lineData ->
                        LineDataItem(index, lineDataIndex, timeId, lineData)
                    }
                }
            }
        }
    }
}

@Composable
fun LineDataItem(index: Int, lineDataIndex: Int, timeId: String, item: Event) {
    Column(
        modifier = Modifier
            .padding(top = if (index != 0 && lineDataIndex == 0) 16.dp else 0.dp)
            .fillMaxWidth()
            .clickable {
                GlobalStore.navigator.navigate(Route.detailAction(timeId = timeId, id = item.id))
            }
            .pointerHoverIcon(PointerIcon.Hand)
            .padding(bottom = 18.dp, start = 16.dp)
    ) {
        Text(item.name, modifier = Modifier.drawWithContent {
            drawContent()
            translate(left = -(size.width / 2 + 16), top = size.height / 6) {
                drawCircle(
                    color = Color.Black,
                    radius = 4f,
                )
            }
        }, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        if (item.description?.isNotBlank() == true) {
            Text(item.description!!, color = Color.LightGray, fontSize = 12.sp, lineHeight = 18.sp)
        }
        if (EventType.DATE_YEAR != item.dateType) {
            Text(
                item.formatDateTime(),
                color = Color.LightGray,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

private fun Event.formatDateTime(): String =
    when (dateType) {
        EventType.DATE_YEAR -> ""
        EventType.DATE_YEAR_MOUTH -> "${date.year}-${date.monthNumber}"
        EventType.DATE -> "${date.year}-${date.monthNumber}-${date.dayOfMonth}"
        EventType.DATE_TIME -> "${date.year}-${date.monthNumber}-${date.dayOfMonth} ${date.hour}:${date.minute}:${date.second}"
    }
