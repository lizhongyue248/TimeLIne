package model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import toDateWithoutYearString

enum class EventDateType {
    DATE_YEAR, DATE_YEAR_MOUTH, DATE, DATE_TIME
}

@Serializable
data class Event(
    val id: String,
    val periodId: String,
    val name: String,
    val description: String?,
    val images: JsonArray? = null,
    val dateType: EventDateType = EventDateType.DATE_TIME,
    val date: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val createDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val updateDate: LocalDateTime? = null
) {
    public val shoeDate: String = when (dateType) {
        EventDateType.DATE_YEAR -> "This Year"
        EventDateType.DATE_YEAR_MOUTH -> "Mouth ${date.monthNumber}"
        EventDateType.DATE, EventDateType.DATE_TIME -> date.toDateWithoutYearString()
    }
}
