package page.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun HomeActionDialog(
    name: String = "",
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var nameState by rememberSaveable { mutableStateOf(name) }
    Dialog(
        onDismissRequest = onDismissRequest
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
                TextField(
                    value = nameState,
                    onValueChange = { nameState = it },
                    singleLine = true,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        if (nameState.isNotBlank()) {
                            onConfirmation(nameState)
                        }
                        onDismissRequest()
                    }, modifier = Modifier.padding(8.dp).pointerHoverIcon(PointerIcon.Hand)) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}