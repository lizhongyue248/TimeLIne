package layout

import NavFadeTransition
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import page.account.AccountContent
import page.event.EventPage
import page.period.PeriodContent
import store.AppStore
import store.GlobalStore
import store.Route
import timeline.composeapp.generated.resources.Res

@Composable
fun NavLayout() {
    NavWrapper { paddingValues ->
        NavHost(
            navigator = GlobalStore.layoutNavigator,
            initialRoute = Route.PERIOD,
            navTransition = NavFadeTransition()
        ) {
            scene(
                route = Route.PERIOD,
            ) {
                PeriodContent(paddingValues)
            }
            scene(
                route = Route.EVENT,
            ) { backStackEntry ->
                val periodId = backStackEntry.path<String>("id")!!
                EventPage(periodId).Content(paddingValues)
            }
            scene(
                route = Route.ACCOUNT
            ) {
                AccountContent(paddingValues)
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun NavWrapper(content: @Composable (PaddingValues) -> Unit = {}) {
    val currentEntry = GlobalStore.layoutNavigator.currentEntry.collectAsState(null)
    val route = currentEntry.value?.route?.route
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().height(68.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                ),
                shape = MaterialTheme.shapes.extraLarge
                    .copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            GlobalStore.layoutNavigator.navigate(Route.PERIOD)
                        },
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(32.dp),
                            tint = if (route === Route.PERIOD) MaterialTheme.colorScheme.tertiaryContainer else Color.LightGray
                        )
                    }
                    IconButton(
                        onClick = {
                            GlobalStore.layoutNavigator.navigate(Route.ACCOUNT)
                        },
                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.fill_account),
                            contentDescription = "Account",
                            modifier = Modifier.size(32.dp),
                            tint = if (route === Route.ACCOUNT) MaterialTheme.colorScheme.tertiaryContainer else Color.LightGray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .offset(y = -(32.dp)),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        if (AppStore.state.periodList.isEmpty()) {
                            coroutineScope.launch {
                                GlobalStore.snackbar.showSnackbar("Please add period first!")
                            }
                        } else {
                            GlobalStore.navigator.navigate(Route.eventActionPath())
                        }
                    },
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