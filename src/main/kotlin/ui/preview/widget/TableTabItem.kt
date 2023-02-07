package ui.preview.widget

import YarmarkaIconPack
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.icon.IcProject
import common.theme.BlueMainLight
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.regular.User

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: ImageVector,
    title: String,
    colorSelected: Color,
    colorUnselected: Color,
    onClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .selectable(
                selected = selected,
            ) {
                println("clicked")
            }
            .background(
                color = if (selected) colorUnselected else colorSelected
            )
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(10.dp)
            )
            .clickable {
                onClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.size(4.dp))
        Icon(
            //painter = painterResource("ic_student.svg"),
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) colorSelected else colorUnselected,
            modifier = Modifier
                .size(60.dp, 60.dp)
                .padding(8.dp)
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = title,
            color = if (selected) colorSelected else colorUnselected,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.size(4.dp))
    }
}

@Composable
fun TabHome(
    selectedTabIndex: Int,
    onSelectedTab: (TabPage) -> Unit,
) {
    TabRow(
        modifier = Modifier.padding(horizontal = 100.dp),
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.Transparent,
        divider = {},
        indicator = {}
    ) {
        TabPage.values().forEachIndexed { index, tabPage ->
            TabItem(
                selected = index == selectedTabIndex,
                icon = tabPage.icon,
                title = tabPage.title,
                colorSelected = Color.White,
                colorUnselected = BlueMainLight,
                onClicked = {
                    onSelectedTab(tabPage)
                }
            )
        }
    }
}

enum class TabPage(
    val title: String,
    val icon: ImageVector,
) {
    Students("Студенты", FontAwesomeIcons.Regular.User),
    Projects("Проекты", YarmarkaIconPack.IcProject),
}