package expect

import DateTimeLoggingFormatter
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.ConsoleWriter
import co.touchlab.kermit.LogWriter

actual fun listLogWriter(): List<LogWriter> =
    listOf(CommonWriter(DateTimeLoggingFormatter), ConsoleWriter(DateTimeLoggingFormatter))