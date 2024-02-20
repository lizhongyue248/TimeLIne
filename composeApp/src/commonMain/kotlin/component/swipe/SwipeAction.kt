package component.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

enum class DragAnchors {
    Start,
    Center,
    End,
}


val DeleteAction = Color(0xFFC62828)
val EditAction = Color(0xFF40C4FF)
val SaveAction = Color(0xFFFFC107)

@Composable
fun SaveAction(modifier: Modifier) {
    Box(
        modifier = modifier.background(SaveAction),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Save",
            color = Color.White,
            fontSize = 12.sp,
        )
    }
}


@Composable
fun EditAction(modifier: Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier.background(EditAction)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Edit",
            color = Color.White,
            fontSize = 12.sp,
        )
    }

}

@Composable
fun DeleteAction(modifier: Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier.background(DeleteAction)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Delete",
            color = Color.White,
            fontSize = 12.sp,
        )
    }

}