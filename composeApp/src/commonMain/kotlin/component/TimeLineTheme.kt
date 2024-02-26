package component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    )
}


private val DarkColorPalette = TimeLineColorLightTwoTokens()
private val LightColorPalette = TimeLineColorDarkTwoTokens()