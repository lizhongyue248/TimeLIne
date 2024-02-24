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
import model.LineData
import model.TimeData
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

    fun addTimeData(
        name: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val data = TimeData(name = name, id = uuid4().toString())
        val list = state.timeList.toMutableList()
        list.add(0, data)
        setState { state.copy(timeList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Add Success", withDismissAction = true)
        }
    }

    fun deleteTimeData(
        id: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.timeList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find time data $id, delete error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find time data $id, delete error.",
                    withDismissAction = true
                )
            }
            return
        }
        list.removeAt(index)
        setState { state.copy(timeList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Delete Success", withDismissAction = true)
        }
    }

    fun editTimeData(
        id: String,
        name: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.timeList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find time data $id, update error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find time data $id, update error.",
                    withDismissAction = true
                )
            }
            return
        }
        val item = list.removeAt(index).copy(name = name, updateDate = now())
        list.add(index, item)
        setState { state.copy(timeList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Update Success", withDismissAction = true)
        }
    }

    fun addLineData(
        data: LineData,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.lineList.toMutableList()
        list.add(0, data)
        setState { state.copy(lineList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Add Success", withDismissAction = true)
        }
    }

    fun editLineData(
        data: LineData,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        val list = state.lineList.toMutableList()
        val index = list.indexOfFirst { it.id == data.id }
        if (index < 0) {
            Logger.w { "Can not find time data ${data.id}, update error." }
            coroutineScope.launch {
                GlobalStore.snackbar.showSnackbar(
                    "Can not find time data ${data.id}, update error.",
                    withDismissAction = true
                )
            }
            return
        }
        list.removeAt(index)
        list.add(index, data)
        setState { state.copy(lineList = list) }
        coroutineScope.launch {
            GlobalStore.snackbar.showSnackbar("Update Success", withDismissAction = true)
        }
    }

    fun deleteLineData(
        id: String
    ) {
        val list = state.lineList.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index < 0) {
            Logger.w { "Can not find time data $id, delete error." }
            return
        }
        list.removeAt(index)
        setState { state.copy(lineList = list) }
    }
}
