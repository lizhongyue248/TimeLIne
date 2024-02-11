
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberDialogState
import component.MDialogWindow
import component.rightBottom
import page.home.HomePageTitle
import java.awt.GraphicsEnvironment

fun main() = application {
    val visible = remember { mutableStateOf(true) }
    val rightBottom = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.rightBottom
    val dialogState = rememberDialogState(
        width = 420.dp,
        height = 700.dp,
        position = WindowPosition((rightBottom.x - 400).dp, (rightBottom.y - 690).dp)
    )

    MDialogWindow(
        onCloseRequest = { }, visible = visible.value,
        alwaysOnTop = true,
        undecorated = true,
        transparent = true,
        resizable = false,
        state = dialogState,
    ) {
        App(
            homeModifier = Modifier.fillMaxWidth()
                .padding(top = 12.dp, bottom = 6.dp, start = 24.dp, end = 12.dp),
            title = {
                WindowDraggableArea {
                    HomePageTitle()
                }
            }
        )
    }
}
