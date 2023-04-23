package ui.details.project.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import common.compose.BorderedTitledComposable
import common.compose.ExposedDropdownMenuStateHolder
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import domain.model.ProjectSpecialty
import domain.model.Specialty

@Composable
fun SpecialtyPicker(
    modifier: Modifier,
    title: String,
    itemsState: SnapshotStateList<ProjectSpecialty>,
    stateHolder: ExposedDropdownMenuStateHolder,
    dropdownItems: List<Specialty>,
    priority: Int,
) {
    BorderedTitledComposable(
        title = title
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            SpecialtyCoursesGrid(itemsState)
            Spacer(Modifier.size(8.dp))
            ExposedProjectSpecialtyDropdownMenu(
                modifier = modifier,
                title = "Специальности",
                stateHolder = stateHolder,
                items = dropdownItems,
            ) { index, clickedItem ->
                val clickedSpecialty = dropdownItems[index]
                if (!itemsState.map { ps -> ps.specialty }.contains(clickedSpecialty)) {
                    itemsState.add(
                        ProjectSpecialty(
                            id = 0,
                            course = null,
                            specialty = clickedSpecialty,
                            priority = priority
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ExposedProjectSpecialtyDropdownMenu(
    modifier: Modifier = Modifier,
    title: String,
    stateHolder: ExposedDropdownMenuStateHolder,
    items: List<Specialty>,
    onItemClicked: (Int, String) -> Unit,
) {
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
                    BorderStroke(2.dp, BlueMainDark),
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    stateHolder.onEnabled(!stateHolder.enabled)
                }
                .padding(12.dp)
        ) {
            Text(
                text = AnnotatedString(title),
                modifier = Modifier.fillMaxWidth(0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = stateHolder.icon,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpecialtyCoursesGrid(
    itemsState: SnapshotStateList<ProjectSpecialty>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsState.chunked(6).forEach { row ->
            Row {
                row.forEach { item ->
                    Chip(
                        onClick = {},
                        enabled = false,
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.White,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, BlueMainDark),
                    ) {
                        SpecialtyCoursesItem(
                            title = item.specialty!!.name,
                            onChangeCourse = {

                            },
                            isSpecial = !item.specialty!!.name.endsWith("б")
                        )
                    }
                    Spacer(Modifier.size(16.dp, 16.dp))
                }
            }
            Spacer(Modifier.size(16.dp, 16.dp))
        }
    }
}

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
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp),
            text = title,
            color = BlueMainLight,
            fontWeight = FontWeight.Bold
        )
        specialtyCourses.forEachIndexed { index, course ->
            CourseItem(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 4.dp,
                        end = if (index == specialtyCourses.lastIndex) 8.dp else 4.dp
                    ),
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
            .clip(RoundedCornerShape(10.dp))
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
            text = title,
            color = BlueMainLight
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