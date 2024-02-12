package page.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import model.TimeData
import model.TimeDataList

@Composable
fun HomeContent(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.consumeWindowInsets(innerPadding),
        contentPadding = innerPadding
    ) {
        items(TimeDataList, key = { it.name }) {
            HomeContentItem(it)
        }
    }
}

@Composable
fun HomeContentItem(item: TimeData) {
    Column(
        modifier = Modifier
            .clickable {  }
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
