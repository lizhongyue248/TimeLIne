package page.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.LineData
import model.LineDataList
import model.LineDateType

@Composable
fun DetailContent(innerPadding: PaddingValues) {
    val data = LineDataList
        .groupBy { it.date.year }
        .mapValues { (_, list) ->
            val sortedList = list.sortedWith(compareByDescending { it.date })
            val dateYearList = sortedList
                .filter { it.dateType == LineDateType.DATE_YEAR }
                .sortedByDescending { it.createDate }
            dateYearList + sortedList.filter { it.dateType != LineDateType.DATE_YEAR }
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
                    modifier = Modifier.padding(end = 8.dp, top = if (index == 0) 0.dp else 18.dp)
                )
                Column(
                    modifier = Modifier
                        .then(Modifier.drawWithContent {
                            drawContent()
                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, 0f),
                                end = Offset(0f, columnHeight.toPx()),
                                strokeWidth = 1.dp.toPx() // Width of the border
                            )
                        })
                        .padding(horizontal = 12.dp)
                ) {
                    list.mapIndexed() { lineDataIndex, lineData ->
                        LineDataItem(index + lineDataIndex, lineData)
                    }
                }
            }
        }
    }
}

@Composable
fun LineDataItem(index: Int, item: LineData) {
    Column(modifier = Modifier.padding(top = if (index == 0) 0.dp else 18.dp)) {
        Text(item.name, modifier = Modifier.drawWithContent {
            drawContent()
            translate(left = -(size.width / 2 + 24f)) {
                drawCircle(
                    color = Color.Black,
                    radius = 6f,
                )
            }
        }, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        if (item.description?.isNotBlank() == true) {
            Text(item.description!!, color = Color.LightGray, fontSize = 12.sp, lineHeight = 18.sp)
        }
        if (LineDateType.DATE_YEAR != item.dateType) {
            Text(item.formatDateTime(), color = Color.LightGray, fontSize = 12.sp, lineHeight = 18.sp)
        }
    }
}

private fun LineData.formatDateTime(): String =
    when (dateType) {
        LineDateType.DATE_YEAR -> ""
        LineDateType.DATE_YEAR_MOUTH -> "${date.year}-${date.monthNumber}"
        LineDateType.DATE -> "${date.year}-${date.monthNumber}-${date.dayOfMonth}"
        LineDateType.DATE_TIME -> "${date.year}-${date.monthNumber}-${date.dayOfMonth} ${date.hour}:${date.minute}:${date.second}"
    }
