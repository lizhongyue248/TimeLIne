package state

import kotlinx.serialization.Serializable
import model.Event
import model.EventDateType
import model.Period

@Serializable
data class ApplicationState(
    val periodList: List<Period>,
    val eventList: List<Event>
) {
    val periodEvent: Map<String, Map<Int, List<Event>>>
        get() = eventList.groupBy { it.periodId }
            .mapValues { item ->
                item.value
                    .groupBy { it.date.year }
                    .mapValues { (_, list) ->
                        val sortedList = list.sortedWith(compareByDescending { it.date })
                        val dateYearList = sortedList
                            .filter { it.dateType == EventDateType.DATE_YEAR }
                            .sortedByDescending { it.createDate }
                        dateYearList + sortedList.filter { it.dateType != EventDateType.DATE_YEAR }
                    }
            }

}