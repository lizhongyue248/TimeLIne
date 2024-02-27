package component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ui.TimeLineColorDarkTwoTokens
import ui.TimeLineColorLightTwoTokens

@Composable
fun TimeLineTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colors = TimeLineColorLightTwoTokens()
    MaterialTheme(
        colorScheme = colors,
        content = content,
        shapes = MaterialTheme.shapes.copy(extraLarge = RoundedCornerShape(24.0.dp))
    )
}


private val DarkColorPalette = TimeLineColorLightTwoTokens()
private val LightColorPalette = TimeLineColorDarkTwoTokens()