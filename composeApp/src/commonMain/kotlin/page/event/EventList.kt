package page.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import component.NoDataPage
import store.AppStore

@Composable
fun ColumnScope.EventList(timeId: String) {
//    val data = AppStore.state.periodEvent[timeId] ?: emptyMap()
//    if (data.isEmpty()) {
//        NoDataPage()
//        return
//    }
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
        LazyColumn(
            modifier = Modifier.padding(20.dp)
        ) {
            item {
                Text("2024", fontSize = 26.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Row(modifier = Modifier.padding(start = 18.dp)) {
                    Text(
                        "5.20",
                        modifier = Modifier.width(60.dp)
                            .drawWithContent {
                                drawContent()
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(-(12f), 0f),
                                    end = Offset(-(12f), columnHeight.toPx()),
                                    strokeWidth = 1.dp.toPx() // Width of the border
                                )
                                translate(left = -(size.width / 2 + 12), top = size.height / 6) {
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
                                "今天的测试就是那么简单，必须拿下！",
                                fontSize = MaterialTheme.typography.titleSmall.fontSize
                            )
                            Text(
                                "12:45",
                                color = Color.LightGray,
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}