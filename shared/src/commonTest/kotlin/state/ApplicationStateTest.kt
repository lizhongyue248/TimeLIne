package state

import com.benasher44.uuid.uuid4
import kotlinx.datetime.LocalDateTime
import model.Event
import model.EventDateType
import kotlin.test.Test

class ApplicationStateTest {

    @Test
    fun filterTimeLineDataOrderTest() {
        ApplicationState(
            periodList = emptyList(),
            eventList = listOf(
                Event(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = EventDateType.DATE,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                Event(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = EventDateType.DATE_YEAR,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                Event(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = EventDateType.DATE_YEAR_MOUTH,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
                Event(
                    uuid4().toString(),
                    "1",
                    "test1",
                    "description",
                    dateType = EventDateType.DATE_TIME,
                    date = LocalDateTime(2024, 1, 1, 11, 1)
                ),
            )
        )
    }


}