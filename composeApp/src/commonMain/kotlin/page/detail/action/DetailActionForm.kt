package page.detail.action

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import component.MTextField
import isNotDigit
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import model.Event
import model.EventDateType
import now
import store.AppStore
import store.GlobalStore
import toDateString
import toLocalDate
import toLocalTime
import toTImeWithoutSecondString

@OptIn(ExperimentalMaterialApi::class)
private val contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
    top = 0.dp,
    bottom = 6.dp,
    start = 0.dp,
    end = 0.dp
)
private val cardModifier = Modifier
    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 24.dp)


@Composable
internal fun DetailActionForm(
    paddingValues: PaddingValues,
    state: MutableState<Event>,
    id: String?
) {
    Column(
        modifier = Modifier
            .consumeWindowInsets(paddingValues)
            .padding(paddingValues)
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Column(
                modifier = cardModifier,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                MTextField(
                    value = state.value.name,
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    onValueChange = { state.value = state.value.copy(name = it) },
                    singleLine = true,
                    placeholder = { Text("Name", color = Color.Gray) },
                    contentPadding = contentPadding,
                )
                MTextField(
                    value = state.value.description ?: "",
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    onValueChange = { state.value = state.value.copy(description = it) },
                    placeholder = { Text("Description", color = Color.Gray) },
                    contentPadding = contentPadding,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("Event Data Time", color = Color.LightGray)
        ElevatedCard(
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Column(
                modifier = cardModifier,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                DetailDateTypeToggle(state)
                DetailFormDate(state)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (id != null) {
            ElevatedButton(onClick = {
                GlobalStore.confirmDialog(
                    title = "Confirm delete this?",
                    onConfirm = {
                        AppStore.deleteEventData(id)
                        GlobalStore.navigator.goBack()
                    }
                )
            }, modifier = Modifier.fillMaxWidth().pointerHoverIcon(PointerIcon.Hand)) {
                Text("Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailFormDate(state: MutableState<Event>) {
    var date by remember { mutableStateOf<LocalDate?>(state.value.date.toLocalDate()) }
    var time by remember { mutableStateOf<LocalTime?>(state.value.date.toLocalTime()) }
    var dateYear by remember { mutableStateOf("${state.value.date.year}") }
    var dateMonth by remember { mutableStateOf("${state.value.date.monthNumber}") }
    var showDatePick by remember { mutableStateOf(false) }
    var showTimePick by remember { mutableStateOf(false) }

    when (state.value.dateType) {
        EventDateType.DATE_TIME -> {
            MTextField(
                modifier = Modifier.fillMaxWidth().height(42.dp),
                placeholder = { Text("Date", color = Color.Gray) },
                onClick = { showDatePick = true },
                singleLine = true,
                readOnly = true,
                contentPadding = contentPadding,
                pointerHand = true,
                suffix = {
                    Row {
                        if (date != null) {
                            Text(date!!.toDateString())
                        }
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = "Localized description",
                            tint = Color.Gray
                        )
                    }
                }
            )
            MTextField(
                modifier = Modifier.fillMaxWidth().height(42.dp),
                placeholder = { Text("Time", color = Color.Gray) },
                onClick = { showTimePick = true },
                readOnly = true,
                contentPadding = contentPadding,
                singleLine = true,
                pointerHand = true,
                suffix = {
                    Row {
                        if (time != null) {
                            Text(time!!.toTImeWithoutSecondString())
                        }
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = "Localized description",
                            tint = Color.Gray
                        )
                    }
                }
            )
        }

        EventDateType.DATE -> MTextField(
            modifier = Modifier.fillMaxWidth().height(42.dp),
            placeholder = { Text("Date", color = Color.Gray) },
            onClick = { showDatePick = true },
            singleLine = true,
            readOnly = true,
            contentPadding = contentPadding,
            pointerHand = true,
            suffix = {
                Row {
                    if (date != null) {
                        Text(date!!.toDateString())
                    }
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = "Localized description",
                        tint = Color.Gray
                    )
                }
            }
        )

        EventDateType.DATE_YEAR -> {
            MTextField(
                value = dateYear,
                modifier = Modifier.fillMaxWidth().height(42.dp),
                onValueChange = {
                    if (it.isEmpty()) {
                        dateYear = ""
                        return@MTextField
                    }
                    if (it.isNotDigit()) {
                        return@MTextField
                    }
                    val year = if (it.toInt() > 9999) {
                        it.substring(0..3).toInt()
                    } else {
                        it.toInt()
                    }
                    dateYear = "$year"
                    date = if (date != null) {
                        LocalDate(year = year, date!!.monthNumber, date!!.dayOfMonth)
                    } else {
                        LocalDate(year = year, 1, 1)
                    }
                },
                placeholder = { Text("Year", color = Color.Gray) },
                singleLine = true,
                contentPadding = contentPadding,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        EventDateType.DATE_YEAR_MOUTH -> {
            MTextField(
                value = dateYear,
                modifier = Modifier.fillMaxWidth().height(42.dp),
                onValueChange = {
                    if (it.isEmpty()) {
                        dateYear = ""
                        return@MTextField
                    }
                    if (it.isNotDigit()) {
                        return@MTextField
                    }
                    val year = if (it.toInt() > 9999) {
                        it.substring(0..3).toInt()
                    } else {
                        it.toInt()
                    }
                    dateYear = "$year"
                    val localDate = if (date != null) {
                        LocalDate(year = year, date!!.monthNumber, 1)
                    } else {
                        LocalDate(year = year, 1, 1)
                    }
                    date = localDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
                    time = LocalTime(23, 59, 59, 999_999_999)
                },
                placeholder = { Text("Year", color = Color.Gray) },
                singleLine = true,
                contentPadding = contentPadding,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            MTextField(
                value = dateMonth,
                modifier = Modifier.fillMaxWidth().height(42.dp),
                onValueChange = {
                    if (it.isEmpty()) {
                        dateMonth = ""
                        return@MTextField
                    }
                    if (it.isNotDigit()) {
                        return@MTextField
                    }
                    val month = if (it.toInt() > 12) {
                        12
                    } else {
                        it.toInt()
                    }
                    dateMonth = "$month"
                    val localDate = if (date != null) {
                        LocalDate(year = date!!.year, month, 1)
                    } else {
                        LocalDate(year = 1970, month, 1)
                    }
                    date = localDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
                    time = LocalTime(23, 59, 59, 999_999_999)
                },
                placeholder = { Text("Mouth", color = Color.Gray) },
                contentPadding = contentPadding,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
    if (showDatePick || showTimePick) {
        val now = now()
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = now.toInstant(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()
        )
        val timePickerState = rememberTimePickerState(
            initialMinute = now.minute,
            initialHour = now.hour,
            is24Hour = true
        )
        DetailFormDialog(
            onDismissRequest = {
                showTimePick = false
                showDatePick = false
            },
            onConfirm = {
                val epochMilliseconds = datePickerState.selectedDateMillis
                if (epochMilliseconds != null && showDatePick) {
                    date = Instant.fromEpochMilliseconds(epochMilliseconds)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .toLocalDate()
                }
                if (showTimePick) {
                    time = LocalTime(timePickerState.hour, timePickerState.minute)
                }
            }
        ) {
            if (showDatePick) {
                DatePicker(
                    datePickerState,
                    title = null,
                    headline = null,
                    showModeToggle = false,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                )
            } else if (showTimePick) {
                TimePicker(timePickerState)
            }
        }
    }

    LaunchedEffect(date, time) {
        if (date != null && time != null) {
            state.value = state.value.copy(
                date = LocalDateTime(date!!, time!!)
            )
        } else if (date != null) {
            state.value = state.value.copy(
                date = LocalDateTime(date!!, LocalTime(0, 1))
            )
        }
    }
}