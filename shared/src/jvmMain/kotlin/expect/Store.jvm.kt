package expect

import CONFIG_PATH
import StoreKeys
import co.touchlab.kermit.Logger
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer

object JvmStore : Store {
    private val fs = FileSystem.SYSTEM
    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    override fun <T> get(key: StoreKeys, defaultValue: T, serializer: KSerializer<T>): T {
        val path = CONFIG_PATH.toPath()
        if (!fs.exists(path)) {
            return defaultValue
        }
        try {
            val configMap = json.decodeFromBufferedSource<Map<StoreKeys, JsonElement>>(
                fs.source(path).buffer()
            )
            val value = configMap[key] ?: return defaultValue
            return json.decodeFromJsonElement(serializer, value)
        } catch (e: SerializationException) {
            Logger.w("Config file format is invalid json file.")
            return defaultValue
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun <T> set(key: StoreKeys, content: T, serializer: KSerializer<T>) {
        val path = CONFIG_PATH.toPath()
        if (path.parent !== null && !fs.exists(path.parent!!)) {
            fs.createDirectories(path.parent!!)
        }
        val contentJsonElement = json.encodeToJsonElement(serializer, content)
        val configMap = if (fs.exists(path)) {
            try {
                json.decodeFromBufferedSource<MutableMap<StoreKeys, JsonElement>>(
                    fs.source(path).buffer()
                )
            } catch (e: SerializationException) {
                Logger.w("Config file format is invalid json file.")
                mutableMapOf()
            }
        } else {
            mutableMapOf()
        }
        configMap[key] = contentJsonElement
        val contentJson = json.encodeToString(configMap)
        fs.write(path) { writeUtf8(contentJson) }
    }

}

actual fun getStore(): Store = JvmStore