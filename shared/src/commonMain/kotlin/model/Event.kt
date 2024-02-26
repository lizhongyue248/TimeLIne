package model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

enum class EventType {
    DATE_YEAR, DATE_YEAR_MOUTH, DATE, DATE_TIME
}

@Serializable
data class Event(
    val id: String,
    val timeId: String,
    val name: String,
    val description: String?,
    val images: JsonArray? = null,
    val dateType: EventType = EventType.DATE_TIME,
    val date: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val createDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val updateDate: LocalDateTime? = null
)
