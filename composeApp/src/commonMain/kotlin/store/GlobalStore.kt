package store

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import expect.Platform
import expect.getPlatform
import moe.tlaster.precompose.navigation.Navigator
import state.AlertState
import state.PeriodDialogState

object GlobalStore {
    val navigator = Navigator()
    var alert by mutableStateOf(AlertState())
        private set
    var periodDialog by mutableStateOf(PeriodDialogState())
        private set

    var snackbar = SnackbarHostState()
    val platform: Platform = getPlatform()

    private inline fun setAlert(update: AlertState.() -> AlertState) {
        alert = alert.update()
    }

    private inline fun setPeriodDialog(update: PeriodDialogState.() -> PeriodDialogState) {
        periodDialog = periodDialog.update()
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

    fun periodEditDialog(
        name: String = "",
        onConfirmation: (String) -> Unit = {},
    ) {
        setPeriodDialog {
            periodDialog.copy(
                name,
                visible = !visible,
                onDismissRequest = { GlobalStore.periodEditDialogHidden() },
                onConfirmation
            )
        }
    }

    private fun periodEditDialogHidden() {
        setPeriodDialog {
            periodDialog.copy(
                visible = false,
            )
        }
    }
}