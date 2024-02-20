package store

import ConfigurationState
import StoreKeys
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import expect.getStore
import moe.tlaster.precompose.navigation.Navigator

object AppStore {
    private val store = getStore()
    val navigator = Navigator()
    var configuration: ConfigurationState by mutableStateOf(
        store.get(
            StoreKeys.Configuration,
            ConfigurationState(name = "TimeLine"),
            ConfigurationState.serializer()
        )
    )
        private set

    private inline fun setConfiguration(update: ConfigurationState.() -> ConfigurationState) {
        configuration = configuration.update()
        store.set(StoreKeys.Configuration, configuration, ConfigurationState.serializer())
    }

    fun setName(name: String) {
        setConfiguration { configuration.copy(name = name) }
    }
}
