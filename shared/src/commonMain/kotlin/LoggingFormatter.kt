import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTimeLoggingFormatter : MessageStringFormatter {
    override fun formatSeverity(severity: Severity) = "[$severity]"
    override fun formatTag(tag: Tag): String = "(${tag.tag})"
    override fun formatMessage(severity: Severity?, tag: Tag?, message: Message): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val dateTimeString =
            "${now.year}-${now.monthNumber.toTwoString()}-${now.dayOfMonth.toTwoString()} ${now.hour.toTwoString()}:${now.minute.toTwoString()}:${now.second.toTwoString()} "
        val sb = StringBuilder(dateTimeString)
        if (severity != null) sb.append(formatSeverity(severity)).append(" ")
        if (tag != null && tag.tag.isNotEmpty()) sb.append(formatTag(tag)).append(" ")
        sb.append(message.message)
        return sb.toString()
    }

}