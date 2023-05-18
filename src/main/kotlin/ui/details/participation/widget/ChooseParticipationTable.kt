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
import domain.model.*
import kotlinx.coroutines.launch
import ui.common.BaseGodViewModel
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import ui.filter.FilterEntity
import ui.filter.FilterNode
import ui.filter.FilterType

private const val KEY = "PROJECT_PARTICIPATION"

@Composable
fun ChooseParticipationTableItem(
    modifier: Modifier = Modifier,
    item: FilterEntity,
    participationDetailsViewModel: ParticipationDetailsViewModel,
    onClicked: (FilterEntity) -> Unit,
) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    if (item !is Student || participationDetailsViewModel.selectedChooseStudents.value.isEmpty()) {
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (item is Student) {
            Text(text = item.group)
        }
    }
}

@Composable
fun ChooseParticipationTableHead(
    modifier: Modifier = Modifier,
    currentText: String,
    showBackButton: Boolean,
    showOutStudentsButton: Boolean,
    onBackClicked: () -> Unit,
    onShowOutStudentsClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            if (showBackButton) {
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
            }
            Text(
                text = currentText,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            )
        }
        if (showOutStudentsButton) {
            Row(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Button(
                    onClick = onShowOutStudentsClicked,
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
                ) {
                    Text("Студенты без заявок")
                }
            }
        }
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ChooseParticipationTable(
    modifier: Modifier = Modifier,
    filterNode: FilterNode,
    viewModel: BaseGodViewModel,
    requiredParticipation: List<Participation>,
    onNodeOpened: (Boolean, Boolean) -> Unit,
    participationDetailsViewModel: ParticipationDetailsViewModel,
    onItemSelected: () -> Unit,
) {
    val filterStack by remember {
        mutableStateOf(ArrayDeque<FilterNode>().apply {
            add(filterNode)
        })
    }

    var currentItems by remember { mutableStateOf(viewModel.getValuesByType(filterStack.last().type)) }
    var currentFilterTitle by remember { mutableStateOf(filterNode.type.title) }

    var showBackButton by remember {
        mutableStateOf(false)
    }

    var showOutStudentsButton by remember {
        mutableStateOf(true)
    }

    rememberCoroutineScope().launch {
        participationDetailsViewModel.requiredParticipation.collect {
            if (filterStack.last().type == FilterType.STUDENT && filterStack.last().type != FilterType.OUT_STUDENTS) {
                currentItems = viewModel.getValuesByType(
                    filterType = FilterType.STUDENT,
                    project = filterStack.last().selectedValue as? Project,
                    requiredParticipation = it
                )
            }
        }
    }

    rememberCoroutineScope().launch {
        participationDetailsViewModel.outStudents.collect {
            if (filterStack.last().type == FilterType.OUT_STUDENTS) {
                currentItems = viewModel.getValuesByType(
                    filterType = FilterType.OUT_STUDENTS,
                    outStudents = it
                )
            }
        }
    }

    val scrollState = rememberForeverLazyListState(KEY)
    val coroutineScope = rememberCoroutineScope()

    val filterTypeOrder = listOf<FilterType>(
        FilterType.INSTITUTE,
        FilterType.DEPARTMENT,
        FilterType.PROJECT,
        FilterType.STUDENT,
    )

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
            showBackButton = showBackButton,
            showOutStudentsButton = showOutStudentsButton,
            onShowOutStudentsClicked = {
                showOutStudentsButton = false
                val currentFilterNode = filterStack.last()
                val newFilterNode = FilterNode(
                    prev = currentFilterNode.type,
                    type = FilterType.OUT_STUDENTS,
                    selectedValue = null,
                    next = null
                )
                filterStack.add(newFilterNode)
                currentItems = viewModel.getValuesByType(
                    filterType = filterStack.last().type,
                    outStudents = participationDetailsViewModel.outStudents.value
                )
                currentFilterTitle = filterStack.last().type.title
            },
            onBackClicked = {
                if (filterStack.last().selectedValue != null || filterStack.last().type == FilterType.OUT_STUDENTS) {
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

                showOutStudentsButton = true
                onItemSelected()
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
                onNodeOpened(filterStack.last().type == FilterType.STUDENT, filterStack.last().type == FilterType.OUT_STUDENTS)
                participationDetailsViewModel.currentProject = filterStack.last().selectedValue as? Project
                showBackButton = filterStack.last().selectedValue != null || filterStack.last().type == FilterType.OUT_STUDENTS
                ChooseParticipationTableItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    item = item,
                    participationDetailsViewModel = participationDetailsViewModel,
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
                                project = clickedFilter as? Project,
                                requiredParticipation = requiredParticipation
                            )
                            currentFilterTitle = filterStack.last().type.title
                        } else {
                            val student = item as Student
                            if (participationDetailsViewModel.selectedChooseStudents.value.map { it.id }
                                    .contains(student.id)) {
                                println("${participationDetailsViewModel.selectedChooseStudents.value.map { it.id }} contains ${student.id}")
                                participationDetailsViewModel.selectedChooseStudents.value.remove(student)
                            } else {
                                participationDetailsViewModel.selectedChooseStudents.value.add(student)
                            }
                        }

                        onItemSelected()
                    }
                )
                Divider()
            }
        }
    }
}
