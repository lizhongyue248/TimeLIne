
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import page.home.HomePage
import page.home.HomePageTitle

@Composable
fun App(
    homeModifier: Modifier = Modifier,
    title: @Composable () -> Unit = { HomePageTitle() }
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = "/home",
            ) {
                scene(
                    route = "/home",
                ) {
                    HomePage(modifier = homeModifier, title = title)
                }
                scene(
                    route = "/setting",
                ) {
                    Button(onClick = { navigator.navigate("/home") }) {
                        Text(text = "home!")
                    }
                }
            }
        }
    }
}
