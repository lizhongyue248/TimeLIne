package page.event.action

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.benasher44.uuid.uuid4
import component.MTextField
import component.PageTitle
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
import model.Period
import now
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import page.detail.action.DetailFormDialog
import store.AppStore
import store.GlobalStore
import timeline.composeapp.generated.resources.Res
import toDateString
import toLocalDate
import toLocalTime
import toTImeWithoutSecondString

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
class EventActionPage(val id: String?, val periodId: String?) {

    private lateinit var state: MutableState<Event>
    private var currentPeriod: Period? = null

    private val contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
        top = 0.dp,
        bottom = 6.dp,
        start = 0.dp,
        end = 0.dp
    )

    @Composable
    private fun Content(paddingValues: PaddingValues) {
        var nameState by rememberSaveable { mutableStateOf("") }
        var descriptionState by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary)
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            FormCard {
                PeriodDropdownList()
                MTextField(
                    value = nameState,
                    modifier = Modifier.fillMaxWidth().height(42.dp),
                    onValueChange = { nameState = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            "Name",
                            color = Color.Gray
                        )
                    },
                    contentPadding = contentPadding,
                )
                MTextField(
                    value = descriptionState ?: "",
                    modifier = Modifier.fillMaxWidth().height(150.dp).padding(top = 12.dp),
                    onValueChange = { descriptionState = it },
                    placeholder = {
                        Text(
                            "Description",
                            color = Color.Gray
                        )
                    },
                    contentPadding = contentPadding,
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                "Event Date Time",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            FormCard {
                EventDateTypeToggle()
                Spacer(modifier = Modifier.height(8.dp))
                EventDateForm()
            }
            Spacer(modifier = Modifier.height(34.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().pointerHoverIcon(PointerIcon.Hand),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface
                )
            ) {
                Text(
                    "Save",
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }

    @Composable
    private fun FormCard(cardContent: @Composable () -> Unit) {
        ElevatedCard(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                cardContent()
            }
        }
    }

    @Composable
    fun Scaffold() {
        state = remember {
            mutableStateOf(
                AppStore.state.eventList.find { it.id == (id ?: "") }
                    ?: Event(
                        id = uuid4().toString(),
                        name = "",
                        description = "",
                        periodId = periodId ?: ""
                    )
            )
        }
        if (periodId != null && periodId.isNotDigit()) {
            currentPeriod = AppStore.state.periodList.find { periodId == it.id }
        } else if (AppStore.state.periodList.isNotEmpty()) {
            currentPeriod = AppStore.state.periodList.first()
        }
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.height(40.dp).shadow(5.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { PageTitle(if (id === null) "New" else "Edit") },
                    navigationIcon = {
                        IconButton(
                            onClick = { GlobalStore.navigator.goBack() },
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            onClick = {
                            },
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
            }
        ) { paddingValues ->
            Content(paddingValues)
        }
        LaunchedEffect(currentPeriod) {
            if (currentPeriod != null) {
                setState(
                    periodId = currentPeriod!!.id
                )
            }
        }
    }

    @Composable
    private inline fun PeriodDropdownList() {
        var expanded by remember { mutableStateOf(false) }
        //initial height set at 0.dp
        var fieldWidth by remember { mutableStateOf(0.dp) }

        // get local density from composable
        val density = LocalDensity.current
        Column {
            MTextField(
                modifier = Modifier.fillMaxWidth().height(42.dp)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .onGloballyPositioned {
                        fieldWidth = with(density) {
                            it.size.width.toDp()
                        }
                    },
                value = currentPeriod?.name ?: "",
                singleLine = true,
                enabled = false,
                placeholder = {
                    Text(
                        "Period",
                        color = Color.Gray
                    )
                },
                contentPadding = contentPadding,
                onClick = { expanded = !expanded },
                suffix = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = ""
                    )
                },
                pointerHand = true
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(fieldWidth)
            ) {
                if (AppStore.state.periodList.isEmpty()) {
                    DropdownMenuItem(
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                        text = { Text("No Data") },
                        onClick = {
                            expanded = false
                        }
                    )
                } else {
                    AppStore.state.periodList
                        .forEach { period ->
                            DropdownMenuItem(
                                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                text = { Text(period.name) },
                                onClick = {
                                    setState(periodId = period.id)
                                    currentPeriod = period
                                    expanded = false
                                }
                            )
                        }
                }
            }
        }
    }


    @Composable
    private fun EventDateTypeToggle() {
        Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf(
                NameEventDateType("Date Time", EventDateType.DATE_TIME),
                NameEventDateType("Date", EventDateType.DATE),
                NameEventDateType("Year Mouth", EventDateType.DATE_YEAR_MOUTH),
                NameEventDateType("Year", EventDateType.DATE_YEAR),
            ).forEachIndexed { index, type ->
                Box(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                        .weight(0.25f)
                        .clip(
                            when (index) {
                                0 -> RoundedCornerShape(
                                    topStartPercent = 48,
                                    bottomStartPercent = 48
                                )

                                3 -> RoundedCornerShape(topEndPercent = 48, bottomEndPercent = 48)
                                else -> RectangleShape
                            }
                        )
                        .clickable { setState(dateType = type.type) }
                        .background(
                            if (state.value.dateType == type.type) MaterialTheme.colorScheme.inverseSurface.copy(
                                alpha = 0.7f
                            ) else MaterialTheme.colorScheme.outline
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        type.name,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        lineHeight = 1.em,
                        color = if (state.value.dateType == type.type) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }

    @Composable
    private fun EventDateForm() {
        var date by remember { mutableStateOf<LocalDate?>(state.value.date.toLocalDate()) }
        var time by remember { mutableStateOf<LocalTime?>(state.value.date.toLocalTime()) }
        var dateYear by remember { mutableStateOf("${state.value.date.year}") }
        var dateMonth by remember { mutableStateOf("${state.value.date.monthNumber}") }
        var showDatePick by remember { mutableStateOf(false) }
        var showTimePick by remember { mutableStateOf(false) }
        when (state.value.dateType) {
            EventDateType.DATE_TIME -> {
                MTextField(
                    modifier = Modifier.fillMaxWidth().height(42.dp).padding(end = 16.dp),
                    singleLine = true,
                    readOnly = true,
                    pointerHand = true,
                    onClick = { showDatePick = true },
                    placeholder = {
                        Text(
                            "Date",
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    contentPadding = contentPadding,
                    prefix = {
                        Icon(
                            painter = painterResource(Res.drawable.form_date),
                            contentDescription = ""
                        )
                    },
                    suffix = {
                        Row {
                            if (date != null) {
                                Text(date!!.toDateString())
                            }
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f)
                            )
                        }
                    }
                )
                MTextField(
                    modifier = Modifier.fillMaxWidth().height(42.dp).padding(end = 16.dp),
                    singleLine = true,
                    readOnly = true,
                    pointerHand = true,
                    onClick = { showTimePick = true },
                    placeholder = {
                        Text(
                            "Time",
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    },
                    contentPadding = contentPadding,
                    prefix = {
                        Icon(
                            painter = painterResource(Res.drawable.form_time),
                            contentDescription = ""
                        )
                    },
                    suffix = {
                        Row {
                            if (time != null) {
                                Text(time!!.toTImeWithoutSecondString())
                            }
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f)
                            )
                        }
                    }
                )
            }

            EventDateType.DATE -> MTextField(
                modifier = Modifier.fillMaxWidth().height(42.dp).padding(end = 16.dp),
                singleLine = true,
                readOnly = true,
                pointerHand = true,
                onClick = { showDatePick = true },
                placeholder = {
                    Text(
                        "Date",
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                contentPadding = contentPadding,
                prefix = {
                    Icon(
                        painter = painterResource(Res.drawable.form_date),
                        contentDescription = ""
                    )
                },
                suffix = {
                    Row {
                        if (date != null) {
                            Text(date!!.toDateString())
                        }
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                            contentDescription = "Localized description",
                            tint = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f)
                        )
                    }
                }
            )

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
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        colors = DatePickerDefaults.colors(
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            selectedYearContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            selectedYearContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        )
                    )
                } else if (showTimePick) {
                    TimePicker(
                        timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.1f),
                            selectorColor = MaterialTheme.colorScheme.onSecondary,
                            containerColor = MaterialTheme.colorScheme.onSecondary.copy(0.2f),
                            clockDialSelectedContentColor = MaterialTheme.colorScheme.secondary,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(
                                0.4f
                            )
                        )
                    )
                }
            }
        }

        LaunchedEffect(date, time) {
            if (date != null && time != null) {
                setState(date = LocalDateTime(date!!, time!!))
            } else if (date != null) {
                setState(date = LocalDateTime(date!!, LocalTime(0, 1)))
            }
        }
    }

    private fun setState(
        periodId: String? = null,
        name: String? = null,
        description: String? = null,
        date: LocalDateTime? = null,
        dateType: EventDateType? = null
    ) {
        state.value = state.value.copy(
            periodId = periodId ?: state.value.periodId,
            name = name ?: state.value.name,
            description = description ?: state.value.description,
            date = date ?: state.value.date,
            dateType = dateType ?: state.value.dateType
        )
    }
}

private data class NameEventDateType(
    val name: String,
    val type: EventDateType
)