import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import net.harawata.appdirs.AppDirsFactory
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import java.io.File

val USER_CONFIG_DIR: String =
    AppDirsFactory.getInstance().getUserConfigDir("TimeLine", null, "zyue")
val CONFIG_PATH: String = USER_CONFIG_DIR + File.separatorChar + "config.json"
val LOGGER_PATH: String = USER_CONFIG_DIR + File.separatorChar + "TimeLine.log"

class FileLoggingFileWriter(private val messageStringFormatter: MessageStringFormatter = DefaultFormatter) :
    LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val system = FileSystem.SYSTEM
        val path = LOGGER_PATH.toPath()
        if (path.parent != null && !path.parent!!.toFile().exists()) {
            system.createDirectories(path.parent!!)
        }
        val formatMessage =
            messageStringFormatter.formatMessage(severity, Tag(tag), Message(message))
        system.appendingSink(path).buffer().writeUtf8(formatMessage)
    }
}