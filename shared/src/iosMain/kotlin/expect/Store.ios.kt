package expect

import StoreKeys
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import settings

object IosStore: Store {
    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> get(key: StoreKeys, defaultValue: T, serializer: KSerializer<T>): T {
        return when(defaultValue) {
            is String -> settings[key.name, defaultValue] as T
            is Boolean -> settings[key.name, defaultValue] as T
            is Double -> settings[key.name, defaultValue] as T
            is Float -> settings[key.name, defaultValue] as T
            is Int -> settings[key.name, defaultValue] as T
            is Long -> settings[key.name, defaultValue] as T
            else -> settings.decodeValue(serializer, key.name, defaultValue)
        }
    }

    @OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
    override fun <T> set(key: StoreKeys, content: T, serializer: KSerializer<T>) {
        when(content) {
            is String -> settings[key.name] = content
            is Boolean -> settings[key.name] = content
            is Double -> settings[key.name] = content
            is Float -> settings[key.name] = content
            is Int -> settings[key.name] = content
            is Long -> settings[key.name] = content
            else -> settings.encodeValue(serializer, key.name, content)
        }
    }

}
actual fun getStore(): Store = IosStore