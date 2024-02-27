package page.period

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import component.dashedBorder
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import store.AppStore
import store.GlobalStore
import timeline.composeapp.generated.resources.Res
import toDateString

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PeriodContent(innerPadding: PaddingValues) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.consumeWindowInsets(innerPadding).fillMaxWidth().padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp).height(IntrinsicSize.Max)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .border(BorderStroke(0.dp, Transparent))
                .padding(top = 24.dp, start = 16.dp, end = 0.dp, bottom = 0.dp)
        ) {
            Box(
                modifier = Modifier.size(32.dp).clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.mark),
                    contentDescription = "",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
            Column(
                modifier = Modifier.padding(start = 26.dp, end = 20.dp, bottom = 28.dp).weight(1f)
            ) {
                Text(
                    "Get Premium!",
                    fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                )
                Text(
                    "Get unlimited access to all our features!",
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box {}
                IconButton(
                    onClick = {},
                    modifier = Modifier.clip(MaterialTheme.shapes.extraLarge)
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.home_premium),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 12.dp).weight(1f),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
            ) {
                Text(
                    "Period",
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = {
                    GlobalStore.periodEditDialog(
                        name = "",
                        onConfirmation = { name ->
                            AppStore.addPeriodData(name, coroutineScope)
                        }
                    )
                }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            PeriodCard(coroutineScope)
        }
    }
}

@Composable
fun PeriodCard(coroutineScope: CoroutineScope) {
    val cardHeight = 120.dp
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 12.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(AppStore.state.periodList) { period ->
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(cardHeight)
                    .pointerHoverIcon(PointerIcon.Hand)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 8.dp,
                        top = 16.dp,
                        bottom = 4.dp
                    ).fillMaxSize(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EventBusy,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        IconButton(
                            onClick = {},
                            modifier = Modifier.clip(RoundedCornerShape(50.dp)).size(18.dp)
                                .background(Color.LightGray).pointerHoverIcon(PointerIcon.Hand)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "",
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Text(
                        period.name,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        period.createDate.toDateString(),
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                        fontStyle = MaterialTheme.typography.titleSmall.fontStyle,
                        color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                    )
//                    Row(
//                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp).height(28.dp),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Box(
//                            modifier = Modifier.width(80.dp).height(28.dp)
//                                .clip(MaterialTheme.shapes.extraLarge)
//                                .background(MaterialTheme.colorScheme.background)
//                                .padding(vertical = 4.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                "100",
//                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
//                                color = MaterialTheme.colorScheme.onTertiary,
//                                modifier = Modifier.fillMaxWidth(),
//                                textAlign = TextAlign.Center,
//                                lineHeight = 0.5.em
//                            )
//                        }
//                    }
                }
            }

        }
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.padding(bottom = 12.dp).height(cardHeight)
                    .dashedBorder(1.dp, 12.dp, Color.LightGray)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .clickable {
                        GlobalStore.periodEditDialog(
                            name = "",
                            onConfirmation = { name ->
                                AppStore.addPeriodData(name, coroutineScope)
                            }
                        )
                    }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize().pointerHoverIcon(PointerIcon.Hand)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = ""
                    )
                }
            }
        }
    }
}
