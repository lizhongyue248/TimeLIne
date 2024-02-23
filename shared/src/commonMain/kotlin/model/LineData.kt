package model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

enum class LineDateType {
    DATE_YEAR, DATE_YEAR_MOUTH, DATE, DATE_TIME
}

@Serializable
data class LineData(
    val id: String,
    val timeId: String,
    val name: String,
    val description: String?,
    val images: JsonArray? = null,
    val dateType: LineDateType = LineDateType.DATE_TIME,
    val date: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val createDate: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val updateDate: LocalDateTime? = null
)

val LineDataList = listOf(
    LineData(
        "1",
        "1",
        "过年假期最后一天",
        "完蛋，假期余额严重不足，感觉好快！",
        date = LocalDateTime(2024, 2, 17, 17, 11, 46)
    ),
    LineData(
        "2", "1", "放假第一天，新年快乐！需要做好很多事情的规划" +
                "才可以哦", "需要去把红包准备好然后收拾屋子打少卫生，还要去买零食\n" +
                "招待亲戚朋友！", date = LocalDateTime(2024, 2, 10, 0, 0, 0),
        dateType = LineDateType.DATE
    ),
    LineData(
        "3", "1", "元旦快乐！", "新年新气象，元旦快乐啊", date = LocalDateTime(2024, 1, 1, 0, 0, 0)
    ),
    LineData(
        "4", "1", "今年好快", "收获了很多，学会了很多，觉得自己真的很厉害。在未来的" +
                "道路上一起学习前行，不断的发展壮大才是最好的人生，所" +
                "以为了这个目标，继续努力吧！", date = LocalDateTime(2023, 1, 1, 0, 0, 0),
        dateType = LineDateType.DATE_YEAR
    ),
    LineData(
        "5", "1", "十二月", "2023最后一个月！加油",
        date = LocalDateTime(2023, 12, 1, 0, 0, 0),
        dateType = LineDateType.DATE_YEAR_MOUTH
    ),
)

