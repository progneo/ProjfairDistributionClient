package ui.details.project.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainDark

@Composable
fun BorderedRadioButtonGroupColumn(
    modifier: Modifier = Modifier,
    titles: List<Title>,
    selected: Int,
    title: String,
    onSelected: (Title) -> Unit = {}
) {
    BorderedTitledComposable(
        modifier = modifier,
        title = title
    ) {
        RadioButtonGroupColumn(modifier, titles, selected, onSelected)
    }
}

@Composable
fun BorderedRadioButtonGroupRow(
    modifier: Modifier = Modifier,
    titles: List<Title>,
    selected: Int,
    title: String,
    onSelected: (Title) -> Unit = {}
) {
    BorderedTitledComposable(
        modifier = modifier,
        title = title
    ) {
        RadioButtonGroupRow(modifier, titles, selected, onSelected)
    }
}

@Composable
fun RadioButtonGroupColumn(
    modifier: Modifier = Modifier,
    titles: List<Title>,
    selected: Int,
    onSelected: (Title) -> Unit = {}
) {
    Column(
        modifier
            .selectableGroup()
    ) {
        RadioButtonGroup(titles, selected, onSelected)
    }
}

@Composable
fun RadioButtonGroupRow(
    modifier: Modifier = Modifier,
    titles: List<Title>,
    selected: Int,
    onSelected: (Title) -> Unit = {}
) {
    Row(
        modifier
            .selectableGroup()
    ) {
        RadioButtonGroup(titles, selected, onSelected)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadioButtonGroup(
    titles: List<Title>,
    selected: Int,
    onSelected: (Title) -> Unit = {}
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(titles[selected]) }

    titles.forEach { text ->
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(56.dp)
                .onClick {
                    onOptionSelected(text)
                    onSelected(text)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (text == selectedOption),
                onClick = {
                    onOptionSelected(text)
                    onSelected(text)
                },
                colors = RadioButtonDefaults.colors(selectedColor = BlueMainDark, unselectedColor = BlueMainDark)
            )
            Text(text = text.titleToDisplay, fontSize = 20.sp, color = BlueMainDark)
            Spacer(Modifier.size(16.dp))
        }
    }
}

data class Title(
    val titleToDisplay: String,
    val name: String? = null
)