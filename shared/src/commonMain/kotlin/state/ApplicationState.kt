package state

import kotlinx.serialization.Serializable
import model.Event
import model.EventType
import model.Period

@Serializable
data class ApplicationState(
    val timeList: List<Period>,
    val lineList: List<Event>
) {
    val timeEvent: Map<String, Map<Int, List<Event>>>
        get() = lineList.groupBy { it.timeId }
            .mapValues { item ->
                item.value
                    .groupBy { it.date.year }
                    .mapValues { (_, list) ->
                        val sortedList = list.sortedWith(compareByDescending { it.date })
                        val dateYearList = sortedList
                            .filter { it.dateType == EventType.DATE_YEAR }
                            .sortedByDescending { it.createDate }
                        dateYearList + sortedList.filter { it.dateType != EventType.DATE_YEAR }
                    }
            }

}