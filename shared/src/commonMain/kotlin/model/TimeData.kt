package model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TimeData(
    val id: Int,
    val name: String,
    val createDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val updateDate: LocalDateTime? = null,
)

val TimeDataList = listOf(
    TimeData(1, "2022年数据", LocalDateTime(2022, 9, 1, 15, 34, 56)),
    TimeData(2, "2023年数据", LocalDateTime(2023, 9, 1, 15, 34, 56)),
    TimeData(3, "2024年数据", LocalDateTime(2024, 9, 1, 15, 34, 56)),
)

