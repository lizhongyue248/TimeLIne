package component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ui.TimeLineColorDarkThreeTokens
import ui.TimeLineColorDarkTwoTokens
import ui.TimeLineColorLightTwoTokens

@Composable
fun TimeLineTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colors = TimeLineColorDarkThreeTokens()
    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}


private val DarkColorPalette = TimeLineColorLightTwoTokens()
private val LightColorPalette = TimeLineColorDarkTwoTokens()