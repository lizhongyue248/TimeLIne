package expect

import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import state.StoreKeys
import state.settings

object WasmStore : Store {
    private val json = Json { ignoreUnknownKeys = true }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StoreKeys, defaultValue: T, serializer: KSerializer<T>): T {
        return when (defaultValue) {
            is String -> settings[key.name, defaultValue] as T
            is Boolean -> settings[key.name, defaultValue] as T
            is Double -> settings[key.name, defaultValue] as T
            is Float -> settings[key.name, defaultValue] as T
            is Int -> settings[key.name, defaultValue] as T
            is Long -> settings[key.name, defaultValue] as T
            else -> {
                val value = settings.getStringOrNull(key.name) ?: return defaultValue
                return json.decodeFromString(serializer, value)
            }
        }
    }

    override fun <T> set(key: StoreKeys, content: T, serializer: KSerializer<T>) {
        when (content) {
            is String -> settings[key.name] = content
            is Boolean -> settings[key.name] = content
            is Double -> settings[key.name] = content
            is Float -> settings[key.name] = content
            is Int -> settings[key.name] = content
            is Long -> settings[key.name] = content
            else -> {
                val contentJson = json.encodeToString(serializer, content)
                settings[key.name] = contentJson
            }
        }
    }

}

actual fun getStore(): Store = WasmStore