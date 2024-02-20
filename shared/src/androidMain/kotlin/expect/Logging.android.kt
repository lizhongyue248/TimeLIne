package expect

import DateTimeLoggingFormatter
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LogcatWriter

actual fun listLogWriter(): List<LogWriter> =
    listOf(LogcatWriter(DateTimeLoggingFormatter), CommonWriter(DateTimeLoggingFormatter))
