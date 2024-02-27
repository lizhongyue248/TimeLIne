import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import component.PageTitle
import component.PeriodActionDialog
import component.TimeLineTheme
import expect.listLogWriter
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import page.detail.DetailPage
import page.detail.action.DetailActionPage
import page.home.HomePage
import page.period.PeriodPage
import page.setting.SettingPage
import store.GlobalStore
import store.Route
import timeline.composeapp.generated.resources.Res

fun NavFadeTransition(
    createTransition: EnterTransition = fadeIn(initialAlpha = 0.5f),
    destroyTransition: ExitTransition = fadeOut(),
    pauseTransition: ExitTransition = fadeOut(),
    resumeTransition: EnterTransition = fadeIn(initialAlpha = 0.5f),
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
    title: @Composable (String) -> Unit = { PageTitle(it) }
) {
    Logger.setLogWriters(listLogWriter())
    Logger.setTag("app")
    PreComposeApp {
        TimeLineTheme {
            NavHost(
                navigator = GlobalStore.navigator,
                initialRoute = Route.PERIOD,
                navTransition = NavFadeTransition()
            ) {
                scene(
                    route = Route.PERIOD,
                ) {
                    PeriodPage()
                }
                scene(
                    route = Route.HOME,
                ) {
                    HomePage(
                        title = { title("Time Line") }
                    )
                }
                scene(
                    route = Route.SETTING,
                ) {
                    SettingPage(
                        title = { title("Settings") }
                    )
                }
                scene(
                    route = Route.DETAIL,
                ) { backStackEntry ->
                    DetailPage(
                        timeId = backStackEntry.path<String>("id")!!,
                        title = { title(backStackEntry.path<String>("name")!!) }
                    )
                }
                scene(
                    route = Route.DETAIL_ACTION
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")
                    val timeId = backStackEntry.path<String>("timeId")!!
                    DetailActionPage(
                        id = id,
                        timeId = timeId,
                        title = {
                            if (id == null) {
                                title("New")
                            } else {
                                title("Edit")
                            }
                        }
                    )
                }
            }
            if (GlobalStore.alert.visible) {
                AppConfirmDialog()
            }
            if (GlobalStore.periodDialog.visible) {
                PeriodActionDialog()
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavWrapper(content: @Composable (PaddingValues) -> Unit = {}) {
    Scaffold(
        modifier = Modifier,

        bottomBar = {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().height(68.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                ),
                shape = MaterialTheme.shapes.extraLarge.copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(32.dp),
                            tint = Color.LightGray
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.fill_account),
                            contentDescription = "Account",
                            modifier = Modifier.size(32.dp),
                            tint = Color.LightGray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .offset(y = -(32.dp))
                    .pointerHoverIcon(PointerIcon.Hand),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(68.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    ) {
        content(it)
    }
}
