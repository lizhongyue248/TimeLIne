package wiki.zyue.configuration

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import configuration.Configuration
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(ExperimentalSerializationApi::class)
val DefaultJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    explicitNulls = false
}

val ApplicationConfiguration = ConfigLoaderBuilder.default()
    .addResourceSource("/application.dev.json", true)
    .addResourceSource("/application.json")
    .build()
    .loadConfigOrThrow<Configuration>()