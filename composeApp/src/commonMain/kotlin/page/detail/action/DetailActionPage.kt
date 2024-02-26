package page.detail.action

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.benasher44.uuid.uuid4
import model.Event
import store.AppStore
import store.GlobalStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailActionPage(
    timeId: String,
    id: String? = null,
    title: @Composable () -> Unit,
) {
    val state = remember {
        mutableStateOf(
            AppStore.state.lineList.find { it.id == (id ?: "") }
                ?: Event(
                    id = uuid4().toString(),
                    name = "",
                    description = "",
                    timeId = timeId
                )
        )
    }

    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = {
            SnackbarHost(hostState = GlobalStore.snackbar)
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.height(40.dp).shadow(5.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = title,
                navigationIcon = {
                    IconButton(
                        onClick = { GlobalStore.navigator.goBack() },
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (id == null) {
                                AppStore.addLineData(state.value, coroutineScope)
                            } else {
                                AppStore.editLineData(state.value, coroutineScope)
                            }
                            GlobalStore.navigator.goBack()
                        },
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Text("Save")
                    }
                }
            )
        },
    ) { paddingValues ->
        DetailActionForm(paddingValues, state, id)
    }
}
