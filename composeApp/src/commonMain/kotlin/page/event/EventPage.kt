package page.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.NoDataPage
import model.Event
import model.Period
import store.AppStore
import store.GlobalStore
import store.Route
import toTImeWithoutSecondString

class EventPage(periodId: String = "0") {

    private val data: Map<Int, List<Event>> = AppStore.state.periodEvent[periodId] ?: emptyMap()
    private val period: Period = AppStore.state.periodList.find { it.id == periodId }!!

    @Composable
    private fun ColumnScope.List() {
        var columnHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .onGloballyPositioned {
                    columnHeight = with(density) {
                        it.size.height.toDp()
                    }
                },
            shape = MaterialTheme.shapes.extraLarge
        ) {
            if (data.isEmpty()) {
                NoDataPage()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(20.dp)
                ) {
                    for (datum in data) {
                        item {
                            Text("${datum.key}", fontSize = 26.sp, fontWeight = FontWeight.Black)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        itemsIndexed(datum.value) { index, item ->
                            EventItem(index, item, columnHeight)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun EventItem(index: Int, event: Event, columnHeight: Dp) {
        Row(modifier = Modifier.padding(start = 18.dp)) {
            Text(
                event.shoeDate,
                modifier = Modifier.width(60.dp)
                    .drawWithContent {
                        drawContent()
                        if (index == 0) {
                            drawLine(
                                color = Color.Black,
                                start = Offset(-(12f), 0f),
                                end = Offset(-(12f), columnHeight.toPx()),
                                strokeWidth = 1.dp.toPx() // Width of the border
                            )
                        }
                        translate(
                            left = -(size.width / 2 + 12),
                            top = size.height / 6
                        ) {
                            drawCircle(
                                color = Color.Black,
                                radius = 5f,
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 2f,
                            )
                        }
                    },
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
            Card(
                modifier = Modifier.padding(start = 16.dp).weight(1f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        event.name,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize
                    )
                    Text(
                        event.date.toTImeWithoutSecondString(),
                        color = Color.LightGray,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

    }

    @Composable
    fun Content(innerPadding: PaddingValues) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)
                .consumeWindowInsets(innerPadding).fillMaxSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    GlobalStore.layoutNavigator.navigate(Route.PERIOD)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "More action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                period.name,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight(1000)
            )
            Text(
                "已经发生了${data.size}个事件",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            List()
        }
    }
}