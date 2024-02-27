
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberDialogState
import component.MDialogWindow
import component.PageTitle
import component.rightBottom
import java.awt.GraphicsEnvironment

fun main() = application {
    val visible = remember { mutableStateOf(true) }
    val rightBottom = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.rightBottom
    val dialogState = rememberDialogState(
        width = 380.dp,
        height = 750.dp,
        position = WindowPosition((rightBottom.x - 380).dp, (rightBottom.y - 750).dp)
    )

    MDialogWindow(
        onCloseRequest = { },
        visible = visible.value,
        alwaysOnTop = true,
        undecorated = true,
        transparent = false,
        resizable = false,
        state = dialogState,
    ) {
        App(
            title = {
                WindowDraggableArea {
                    PageTitle(it)
                }
            }
        )
    }
}
