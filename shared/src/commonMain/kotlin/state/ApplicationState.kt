package state

import kotlinx.serialization.Serializable
import model.LineData
import model.LineDateType
import model.TimeData

@Serializable
data class ApplicationState(
    val timeList: List<TimeData>,
    val lineList: List<LineData>
) {
    val timeLineData: Map<String, Map<Int, List<LineData>>>
        get() = lineList.groupBy { it.timeId }
            .mapValues { item ->
                item.value
                    .groupBy { it.date.year }
                    .mapValues { (_, list) ->
                        val sortedList = list.sortedWith(compareByDescending { it.date })
                        val dateYearList = sortedList
                            .filter { it.dateType == LineDateType.DATE_YEAR }
                            .sortedByDescending { it.createDate }
                        dateYearList + sortedList.filter { it.dateType != LineDateType.DATE_YEAR }
                    }
            }

}