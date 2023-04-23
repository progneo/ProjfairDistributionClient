package common.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.TimesCircle
import ui.filter.FilterEntity

@Composable
fun ExposedDropdownMenuWithChips(
    modifier: Modifier = Modifier,
    title: String,
    isTitleChangeable: Boolean,
    stateHolder: ExposedDropdownMenuStateHolder,
    itemsState: SnapshotStateList<String>,
    dropdownItems: List<String>,
    toShortName: String.() -> String,
) {
    BorderedTitledComposable(title = "Преподаватели") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            ChipsVerticalGrid(itemsState, toShortName)
            Spacer(Modifier.size(8.dp))
            ExposedDropdownMenu(
                modifier = modifier,
                title = title,
                isTitleChangeable = isTitleChangeable,
                stateHolder = stateHolder,
                items = dropdownItems,
                toShortName = toShortName
            ) { _, clickedItem ->
                if (!itemsState.contains(clickedItem)) {
                    itemsState.add(clickedItem)
                }
            }
        }
    }
}

@Composable
fun <T: FilterEntity> ExposedTypedDropdownMenuWithChips(
    modifier: Modifier = Modifier,
    title: String,
    isTitleChangeable: Boolean,
    stateHolder: ExposedDropdownMenuStateHolder,
    itemsState: SnapshotStateList<String>,
    dropdownItems: List<T>,
    toShortName: String.() -> String,
) {
    BorderedTitledComposable(title = "Преподаватели") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            ChipsVerticalGrid(itemsState, toShortName)
            Spacer(Modifier.size(8.dp))
            ExposedFilterDropdownMenu<T>(
                modifier = modifier,
                title = title,
                isTitleChangeable = isTitleChangeable,
                stateHolder = stateHolder,
                items = dropdownItems,
            ) { _, clickedItem ->
                if (!itemsState.contains(clickedItem)) {
                    itemsState.add(clickedItem)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChipsVerticalGrid(
    itemsState: SnapshotStateList<String>,
    toShortName: String.() -> String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsState.chunked(3).forEach { threeRow ->
            Row {
                threeRow.forEach { item ->
                    Chip(
                        onClick = {},
                        enabled = false,
                        colors = ChipDefaults.chipColors(
                            backgroundColor = BlueMainLight,
                            contentColor = Color.White,
                            disabledBackgroundColor = BlueMainLight,
                            disabledContentColor = Color.White
                        ),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(item.toShortName())
                            Spacer(Modifier.size(8.dp, 1.dp))
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.TimesCircle,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .onClick {
                                        itemsState.removeIf { str -> str == item }
                                    }
                            )
                        }
                    }
                    Spacer(Modifier.size(16.dp, 1.dp))
                }
            }
        }
    }
}

@Composable
fun ExposedDropdownMenu(
    modifier: Modifier = Modifier,
    title: String,
    isTitleChangeable: Boolean,
    stateHolder: ExposedDropdownMenuStateHolder,
    items: List<String>,
    isEnabled: Boolean = true,
    toShortName: String.() -> String,
    onItemClicked: (Int, String) -> Unit,
) {
    var changeableTitle by remember { mutableStateOf(title) }

    if (!isEnabled) {
        changeableTitle = items[0]
    }

    Box(
        modifier = modifier
            .clickable(false) {}
            .onGloballyPositioned {
                stateHolder.onSize(it.size.toSize())
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .border(
                    BorderStroke(2.dp, if (isEnabled) BlueMainDark else Color.Gray),
                    RoundedCornerShape(10.dp)
                )
                .clickable(enabled = isEnabled) {
                    stateHolder.onEnabled(!stateHolder.enabled)
                }
                .padding(12.dp)
        ) {
            Text(
                text = AnnotatedString(if (isEnabled) changeableTitle.toShortName() else ""),
                modifier = Modifier.fillMaxWidth(0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = if (isEnabled) stateHolder.icon else stateHolder.disabledIcon,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isEnabled) {
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabled(false)
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        stateHolder.size.width.toDp()
                    }),
            ) {
                stateHolder.setItems(items)
                stateHolder.items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            stateHolder.onEnabled(false)
                            onItemClicked(index, item)
                            if (isTitleChangeable) changeableTitle = item
                        }
                    ) {
                        Text(text = item.toShortName())
                    }
                }
            }
        }
    }
}

@Composable
fun <T : FilterEntity> ExposedFilterDropdownMenu(
    modifier: Modifier = Modifier,
    title: String,
    isTitleChangeable: Boolean,
    stateHolder: ExposedDropdownMenuStateHolder,
    items: List<T>,
    isEnabled: Boolean = true,
    isReset: Boolean = false,
    onItemClicked: (Int, String) -> Unit,
) {
    var changeableTitle by remember { mutableStateOf(title) }

    if (!isEnabled || isReset) {
        changeableTitle = items[0].name
    }

    Box(
        modifier = modifier
            .clickable(false) {}
            .onGloballyPositioned {
                stateHolder.onSize(it.size.toSize())
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .border(
                    BorderStroke(2.dp, if (isEnabled) BlueMainDark else Color.Gray),
                    RoundedCornerShape(10.dp)
                )
                .clickable(enabled = isEnabled) {
                    stateHolder.onEnabled(!stateHolder.enabled)
                }
                .padding(12.dp)
        ) {
            Text(
                text = AnnotatedString(if (isEnabled) changeableTitle else ""),
                modifier = Modifier.fillMaxWidth(0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = if (isEnabled) stateHolder.icon else stateHolder.disabledIcon,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (isEnabled) {
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabled(false)
                },
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        stateHolder.size.width.toDp()
                    }),
            ) {
                stateHolder.setItems(items.map { it.name })
                stateHolder.items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            stateHolder.onEnabled(false)
                            onItemClicked(index, item)
                            if (isTitleChangeable) changeableTitle = item
                        }
                    ) {
                        Text(text = item)
                    }
                }
            }
        }
    }
}

class ExposedDropdownMenuStateHolder {
    var enabled by mutableStateOf(false)
        private set
    var size by mutableStateOf(Size.Zero)
        private set
    val icon: ImageVector
        @Composable get() = if (enabled) Icons.Default.KeyboardArrowUp
        else Icons.Default.KeyboardArrowDown
    val disabledIcon: ImageVector
        @Composable get() = Icons.Default.KeyboardArrowDown
    var items: List<String> = emptyList()
        private set

    fun setItems(newItems: List<String>) {
        items = newItems
    }

    fun onEnabled(newValue: Boolean) {
        enabled = newValue
    }

    fun onSize(newValue: Size) {
        size = newValue
    }
}

@Composable
fun rememberExposedMenuStateHolder() = remember {
    ExposedDropdownMenuStateHolder()
}