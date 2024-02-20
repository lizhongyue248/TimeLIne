import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import store.GlobalStore
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
                navigator = GlobalStore.navigator,
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
            if (GlobalStore.alert.visible) {
                AppConfirmDialog()
            }
        }
    }
}

@Composable
fun AppConfirmDialog() {
    AlertDialog(
        title = {
            if (GlobalStore.alert.title.isNotBlank()) {
                Text(text = GlobalStore.alert.title)
            }
        },
        text = {
            if (GlobalStore.alert.text.isNotBlank()) {
                Text(text = GlobalStore.alert.text)
            }
        },
        onDismissRequest = { GlobalStore.trigger() },
        confirmButton = {
            TextButton(
                onClick = {
                    GlobalStore.alert.onConfirm()
                    GlobalStore.trigger()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { GlobalStore.trigger() }
            ) {
                Text("Dismiss")
            }
        }
    )
}
