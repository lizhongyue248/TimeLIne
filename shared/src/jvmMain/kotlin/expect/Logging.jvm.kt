package expect

import DateTimeLoggingFormatter
import FileLoggingFileWriter
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.LogWriter

actual fun listLogWriter(): List<LogWriter> =
    listOf(CommonWriter(DateTimeLoggingFormatter), FileLoggingFileWriter(DateTimeLoggingFormatter))

