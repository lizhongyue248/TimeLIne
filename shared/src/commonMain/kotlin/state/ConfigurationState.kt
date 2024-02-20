package state

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable

enum class StoreKeys {
    Configuration,
    Application;
}

val settings: Settings by lazy { Settings() }

@Serializable
data class ConfigurationState(
    val name: String
)

