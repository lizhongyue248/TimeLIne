import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import component.PageTitle
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import page.home.HomePage
import page.setting.SettingPage

fun NavFadeTransition(
    createTransition: EnterTransition = fadeIn(),
    destroyTransition: ExitTransition = fadeOut(),
    pauseTransition: ExitTransition = fadeOut(),
    resumeTransition: EnterTransition = fadeIn(),
    enterTargetContentZIndex: Float = 0f,
    exitTargetContentZIndex: Float = 0f,
) = object : NavTransition {
    override val createTransition: EnterTransition = createTransition
    override val destroyTransition: ExitTransition = destroyTransition
    override val pauseTransition: ExitTransition = pauseTransition
    override val resumeTransition: EnterTransition = resumeTransition
    override val enterTargetContentZIndex: Float = enterTargetContentZIndex
    override val exitTargetContentZIndex: Float = exitTargetContentZIndex
}

@Composable
fun App(
    pageModifier: Modifier = Modifier,
    title: @Composable (String) -> Unit = { PageTitle(it) }
) {
    PreComposeApp {
        val navigator = rememberNavigator()
        MaterialTheme {
            NavHost(
                navigator = navigator,
                initialRoute = "/home",
                navTransition = NavFadeTransition()
            ) {
                scene(
                    route = "/home",
                ) {
                    HomePage(
                        modifier = pageModifier,
                        title = { title("Time Line") },
                        navigator = navigator
                    )
                }
                scene(
                    route = "/setting",
                ) {
                    SettingPage(
                        modifier = pageModifier,
                        title = { title("Settings") },
                        navigator = navigator
                    )
                }
            }
        }
    }
}
