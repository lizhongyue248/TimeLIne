package expect

import DateTimeLoggingFormatter
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.OSLogWriter

actual fun listLogWriter(): List<LogWriter>
    = listOf(OSLogWriter(DateTimeLoggingFormatter), CommonWriter(DateTimeLoggingFormatter))