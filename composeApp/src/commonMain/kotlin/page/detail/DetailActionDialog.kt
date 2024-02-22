package page.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailActionDialog() {
    val boxModifier = Modifier
    Box(
        modifier = boxModifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {  }
}