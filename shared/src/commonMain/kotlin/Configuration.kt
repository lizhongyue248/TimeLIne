import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable

enum class StoreKeys {
    Configuration;
}

val settings: Settings by lazy { Settings() }

@Serializable
data class ConfigurationState(
    val name: String
)

