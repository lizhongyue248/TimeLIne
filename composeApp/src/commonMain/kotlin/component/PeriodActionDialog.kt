package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import store.GlobalStore


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PeriodActionDialog() {
    var nameState by rememberSaveable { mutableStateOf(GlobalStore.periodDialog.name) }
    Dialog(
        onDismissRequest = GlobalStore.periodDialog.onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Input Name",
                    modifier = Modifier.padding(top = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                MTextField(
                    value = nameState,
                    modifier = Modifier.fillMaxWidth().height(42.dp).padding(vertical = 0.dp, horizontal = 24.dp),
                    onValueChange = { nameState = it },
                    singleLine = true,
                    placeholder = { Text("Name", color = Color.Gray) },
                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                        top = 0.dp,
                        bottom = 6.dp,
                        start = 0.dp,
                        end = 0.dp
                    ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { GlobalStore.periodDialog.onDismissRequest() },
                        modifier = Modifier.padding(8.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        if (nameState.isNotBlank()) {
                            GlobalStore.periodDialog.onConfirmation(nameState)
                        }
                        GlobalStore.periodDialog.onDismissRequest()
                    }, modifier = Modifier.padding(8.dp).pointerHoverIcon(PointerIcon.Hand)) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}