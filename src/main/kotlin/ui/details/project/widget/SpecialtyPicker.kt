package ui.details.project.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theme.BlueMainDark
import common.theme.BlueMainLight

@Composable
fun SpecialtyCoursesItem(
    title: String,
    onChangeCourse: (List<SpecialtyCourse>) -> Unit,
    isSpecial: Boolean = false,
    predefinedSpecialtyCourses: List<SpecialtyCourse> = emptyList(),
) {
    val specialtyCourses = if (isSpecial) listOf(3, 4, 5) else listOf(3, 4)

    val newSelected = if (predefinedSpecialtyCourses.isEmpty()) listOf(true, true, true)
    else predefinedSpecialtyCourses.map { it.selected }

    val selectedIndexes = remember {
        mutableStateListOf<Boolean>(*newSelected.toTypedArray())
    }

    Row(
        modifier = Modifier
            .border(
                border = BorderStroke(2.dp, BlueMainDark),
                shape = RoundedCornerShape(10.dp),
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            text = title,
            //fontWeight = FontWeight.Bold
        )
        specialtyCourses.forEachIndexed { index, course ->
            CourseItem(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = if (index == specialtyCourses.lastIndex) 8.dp else 4.dp),
                title = course.toString(),
                selected = selectedIndexes[index],
                onClick = {
                    selectedIndexes[index] = !selectedIndexes[index]
                    onChangeCourse(
                        List<SpecialtyCourse>(specialtyCourses.size) { index ->
                            SpecialtyCourse(specialtyCourses[index], selectedIndexes[index])
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .border(
                border = BorderStroke(2.dp, BlueMainLight),
                shape = RoundedCornerShape(10.dp),
            )
            .clickable {
                onClick()
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            text = title
        )
        RadioButton(
            modifier = Modifier.padding(4.dp),
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = BlueMainDark, unselectedColor = Color.Gray)
        )
    }
}

data class SpecialtyCourse(
    val course: Int,
    var selected: Boolean,
)