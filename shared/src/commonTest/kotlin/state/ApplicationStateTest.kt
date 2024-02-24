package state

import com.benasher44.uuid.uuid4
import kotlinx.datetime.LocalDateTime
import model.LineData
import model.LineDateType
import kotlin.test.Test

class ApplicationStateTest {

    @Test
    fun filterTimeLineDataOrderTest() {
        ApplicationState(
            timeList = emptyList(),
            lineList = listOf(
                LineData(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = LineDateType.DATE,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                LineData(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = LineDateType.DATE_YEAR,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                LineData(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = LineDateType.DATE_YEAR_MOUTH,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                LineData(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = LineDateType.DATE_TIME,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
            )
        )
    }


}