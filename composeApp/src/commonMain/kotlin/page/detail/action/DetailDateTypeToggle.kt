package page.detail.action

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import model.LineData
import model.LineDateType


@Composable
fun DetailDateTypeToggle(state: MutableState<LineData>) {
    Row {
        FilterChip(
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            onClick = { state.value = state.value.copy(dateType = LineDateType.DATE_TIME)},
            label = { Text("Date Time") },
            selected = state.value.dateType == LineDateType.DATE_TIME,
            shape = RoundedCornerShape(topStartPercent = 10, bottomStartPercent = 10),
            border = FilterChipDefaults.filterChipBorder(
                true,
                state.value.dateType == LineDateType.DATE_TIME,
                selectedBorderColor = Color.Black
            )
        )
        FilterChip(
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            onClick = { state.value = state.value.copy(dateType = LineDateType.DATE) },
            label = { Text("Date") },
            selected = state.value.dateType == LineDateType.DATE,
            shape = RectangleShape,
            border = FilterChipDefaults.filterChipBorder(
                true,
                state.value.dateType == LineDateType.DATE,
                selectedBorderColor = Color.Black
            )
        )
        FilterChip(
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            onClick = { state.value = state.value.copy(dateType = LineDateType.DATE_YEAR_MOUTH) },
            label = { Text("Year Mouth") },
            selected = state.value.dateType == LineDateType.DATE_YEAR_MOUTH,
            shape = RectangleShape,
            border = FilterChipDefaults.filterChipBorder(
                true,
                state.value.dateType == LineDateType.DATE_YEAR_MOUTH,
                selectedBorderColor = Color.Black
            )
        )
        FilterChip(
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            onClick = { state.value = state.value.copy(dateType = LineDateType.DATE_YEAR) },
            label = { Text("Year") },
            selected = state.value.dateType == LineDateType.DATE_YEAR,
            shape = RoundedCornerShape(topEndPercent = 10, bottomEndPercent = 10),
            border = FilterChipDefaults.filterChipBorder(
                true,
                state.value.dateType == LineDateType.DATE_YEAR,
                selectedBorderColor = Color.Black
            )
        )
    }
}