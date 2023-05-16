package ui.details.participation.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import common.theme.BlueMainLight25
import common.theme.GrayLight
import domain.model.Department
import domain.model.Institute
import domain.model.Project
import domain.model.Student
import kotlinx.coroutines.launch
import ui.common.BaseGodViewModel
import ui.filter.FilterEntity
import ui.filter.FilterNode
import ui.filter.FilterType

private const val KEY = "PROJECT_PARTICIPATION"

@Composable
fun ChooseParticipationTableItem(
    modifier: Modifier = Modifier,
    item: FilterEntity,
    onClicked: (FilterEntity) -> Unit,
) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    if (item !is Student) {
        isSelected = false
    }

    Row(
        modifier = modifier
            .clickable {
                onClicked(item)
                if (item is Student) {
                    isSelected = !isSelected
                }
            }
            .background(
                if (item is Student) {
                    if (isSelected) BlueMainLight25
                    else Color.Transparent
                } else Color.Transparent
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ChooseParticipationTableHead(
    modifier: Modifier = Modifier,
    currentText: String,
    onBackClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = { onBackClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = BlueMainDark
            )
        }
        Spacer(Modifier.size(16.dp))
        Text(
            text = currentText,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterVertically)
        )
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ChooseParticipationTable(
    modifier: Modifier = Modifier,
    filterNode: FilterNode,
    viewModel: BaseGodViewModel,
    onItemSelected: (List<Student>, Boolean) -> Unit
) {
    val filterStack by remember {
        mutableStateOf(ArrayDeque<FilterNode>().apply {
            add(filterNode)
        })
    }

    var currentItems by remember { mutableStateOf(viewModel.getValuesByType(filterStack.last().type)) }
    var currentFilterTitle by remember { mutableStateOf(filterNode.type.title) }

    val scrollState = rememberForeverLazyListState(KEY)
    val coroutineScope = rememberCoroutineScope()

    val filterTypeOrder = listOf<FilterType>(
        FilterType.INSTITUTE,
        FilterType.DEPARTMENT,
        FilterType.PROJECT,
        FilterType.STUDENT,
    )

    val selectedStudents by rememberSaveable {
        mutableStateOf<MutableList<Student>>(mutableListOf())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(10.dp)
            )
    ) {
        ChooseParticipationTableHead(
            modifier = Modifier.fillMaxWidth(),
            currentText = currentFilterTitle,
            onBackClicked = {
                if (filterStack.last().selectedValue != null) {
                    filterStack.removeLast()
                    val filterValue = filterStack.last().selectedValue
                    currentItems = viewModel.getValuesByType(
                        filterType = filterStack.last().type,
                        institute = filterValue as? Institute,
                        department = filterValue as? Department,
                        project = filterValue as? Project
                    )
                    currentFilterTitle = filterStack.last().type.title
                }

                onItemSelected(selectedStudents, false)
            }
        )

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    }
                ),

            ) {
            items(currentItems) { item ->
                ChooseParticipationTableItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    item = item,
                    onClicked = { clickedFilter ->
                        val currentFilterNode = filterStack.last()
                        if (currentFilterNode.next != null) {
                            val newFilterNode = FilterNode(
                                prev = currentFilterNode.type,
                                type = currentFilterNode.next,
                                selectedValue = clickedFilter,
                                next = if (filterStack.size + 1 == filterTypeOrder.size) null
                                else {
                                    filterTypeOrder[filterStack.size + 1]
                                }
                            )
                            filterStack.addLast(newFilterNode)
                            currentItems = viewModel.getValuesByType(
                                filterType = filterStack.last().type,
                                institute = clickedFilter as? Institute,
                                department = clickedFilter as? Department,
                                project = clickedFilter as? Project
                            )
                            currentFilterTitle = filterStack.last().type.title
                        } else {
                            val student = item as Student
                            if (selectedStudents.map { it.id }.contains(student.id)) {
                                selectedStudents.remove(student)
                            } else {
                                selectedStudents.add(student)
                            }
                        }

                        onItemSelected(selectedStudents, item is Student)
                    }
                )
                Divider()
            }
        }
    }
}
