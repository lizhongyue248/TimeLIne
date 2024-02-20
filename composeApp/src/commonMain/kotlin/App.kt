import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import component.PageTitle
import expect.listLogWriter
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import page.detail.DetailPage
import page.home.HomePage
import page.setting.SettingPage
import store.AppStore
import store.Route

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
    Logger.setLogWriters(listLogWriter())
    Logger.setTag("app")
    PreComposeApp {
        MaterialTheme {
            NavHost(
                navigator = AppStore.navigator,
                initialRoute = Route.HOME,
                navTransition = NavFadeTransition()
            ) {
                scene(
                    route = Route.HOME,
                ) {
                    HomePage(
                        modifier = pageModifier,
                        title = { title("Time Line") }
                    )
                }
                scene(
                    route = Route.SETTING,
                ) {
                    SettingPage(
                        modifier = pageModifier,
                        title = { title("Settings") }
                    )
                }
                scene(
                    route = Route.DETAIL,
                ) { backStackEntry ->
                    DetailPage(
                        modifier = pageModifier,
                        id = backStackEntry.path<Int>("id")!!,
                        title = { title("Settings") }
                    )
                }
            }
        }
    }
}
