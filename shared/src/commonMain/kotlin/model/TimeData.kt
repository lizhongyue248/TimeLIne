package model

import kotlinx.datetime.LocalDateTime

data class TimeData(
    val name: String,
    val createDate: LocalDateTime
)

val TimeDataList = listOf(
    TimeData("2022年数据", LocalDateTime(2022, 9, 1, 15, 34, 56)),
    TimeData("2023年数据", LocalDateTime(2023, 9, 1, 15, 34, 56)),
    TimeData("2024年数据", LocalDateTime(2024, 9, 1, 15, 34, 56)),
)

