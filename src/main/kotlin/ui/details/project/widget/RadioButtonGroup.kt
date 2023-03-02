package ui.details.project.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun RadioButtonGroup(
    modifier: Modifier = Modifier,
    titles: List<String>,
    selected: Int,
    title: String,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(titles[selected]) }

    BorderedTitledComposable(
        modifier = modifier,
        title = title
    ) {
        Column(
            modifier
                .selectableGroup()
                .border(
                    BorderStroke(2.dp, BlueMainDark),
                    RoundedCornerShape(10.dp)
                )
        ) {
            titles.forEach { text ->
                Row(Modifier.fillMaxWidth().height(56.dp), verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
                        colors = RadioButtonDefaults.colors(selectedColor = BlueMainDark, unselectedColor = BlueMainDark)
                    )
                    Text(text = text, fontSize = 20.sp, color = BlueMainDark)
                }
            }
        }
    }
}