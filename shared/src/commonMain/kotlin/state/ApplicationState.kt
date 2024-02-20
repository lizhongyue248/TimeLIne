package state

import kotlinx.serialization.Serializable
import model.LineData
import model.TimeData

@Serializable
data class ApplicationState(
    val timeList: List<TimeData>,
    val lineList: List<LineData>
)