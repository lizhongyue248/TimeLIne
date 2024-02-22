package store

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import expect.Platform
import expect.getPlatform
import moe.tlaster.precompose.navigation.Navigator
import state.AlertState

object GlobalStore {
    val navigator = Navigator()
    var alert by mutableStateOf(AlertState())
        private set

    var snackbar = SnackbarHostState()
    val platform: Platform = getPlatform()

    private inline fun setAlert(update: AlertState.() -> AlertState) {
        alert = alert.update()
    }

    fun trigger() {
        setAlert { alert.copy(visible = !visible) }
    }

    fun confirmDialog(
        title: String = "Confirm",
        text: String = "",
        onConfirm: () -> Unit = {}
    ) {
        setAlert {
            alert.copy(
                visible = !visible,
                title,
                text,
                onConfirm
            )
        }
    }

}