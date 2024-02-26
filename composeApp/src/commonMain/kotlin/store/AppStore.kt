package store

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.benasher44.uuid.uuid4
import expect.getStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Event
import model.Period
import now
import state.ApplicationState
import state.ConfigurationState
import state.StoreKeys

object AppStore {
    private val store = getStore()
    var configuration: ConfigurationState by mutableStateOf(
        store.get(
            StoreKeys.Configuration,
            ConfigurationState(name = "TimeLine"),
            ConfigurationState.serializer()
        )
    )
        private set
    var state: ApplicationState by mutableStateOf(
        store.get(
            StoreKeys.Application,
            ApplicationState(emptyList(), emptyList()),
            ApplicationState.serializer()
        )
    )

    private inline fun setConfiguration(update: ConfigurationState.() -> ConfigurationState) {
        configuration = configuration.update()
        CoroutineScope(Dispatchers.Default).launch {
            store.set(StoreKeys.Configuration, configuration, ConfigurationState.serializer())
        }
    }

    private inline fun setState(update: ApplicationState.() -> ApplicationState) {
        state = state.update()
        CoroutineScope(Dispatchers.Default).launch {
            store.set(StoreKeys.Application, state, ApplicationState.serializer())
        }
    }

    fun setName(name: String) {
        setConfiguration { configuration.copy(name = name) }
    }

    fun addPeriodData(
        name: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val data = Period(name = name, id = uuid4().toString())
        val list = state.periodList.toMutableList()
        list.add(0, data)
        setState { state.copy(periodList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Add period Success", withDismissAction = true)
        }
    }

    fun deletePeriodData(
        id: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.periodList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find period data $id, delete error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find period data $id, delete error.",
                    withDismissAction = true
                )
            }
            return
        }
        list.removeAt(index)
        setState { state.copy(periodList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Delete period Success", withDismissAction = true)
        }
    }

    fun editPeriodData(
        id: String,
        name: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.periodList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find period data $id, update error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find period data $id, update error.",
                    withDismissAction = true
                )
            }
            return
        }
        val item = list.removeAt(index).copy(name = name, updateDate = now())
        list.add(index, item)
        setState { state.copy(periodList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Update period success", withDismissAction = true)
        }
    }

    fun addEventData(
        data: Event,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.eventList.toMutableList()
        list.add(0, data)
        setState { state.copy(eventList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Add event success", withDismissAction = true)
        }
    }

    fun editEventData(
        data: Event,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.eventList.toMutableList()
        val index = list.indexOfFirst { it.id == data.id }
        if (index < 0) {
            Logger.w { "Can not find event data ${data.id}, update error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find event data ${data.id}, update error.",
                    withDismissAction = true
                )
            }
            return
        }
        list.removeAt(index)
        list.add(index, data)
        setState { state.copy(eventList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Update Success", withDismissAction = true)
        }
    }

    fun deleteEventData(
        id: String
    ) {
        val list = state.eventList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find event data $id, delete error." }
            return
        }
        list.removeAt(index)
        setState { state.copy(eventList = list) }
    }
}
