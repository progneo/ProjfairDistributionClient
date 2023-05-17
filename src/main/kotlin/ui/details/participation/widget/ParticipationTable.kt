package ui.details.participation.widget

import androidx.compose.animation.scaleOut
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainLight
import common.theme.BlueMainLight25
import common.theme.GrayLight
import common.theme.WhiteDark
import domain.model.Participation
import domain.model.Student
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.BaseGodViewModel
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import ui.details.project.di.ProjectDetailsComponent
import ui.preview.viewmodel.PreviewViewModel

private const val KEY = "PROJECT_PARTICIPATION"

@Composable
fun ParticipationTableItem(
    modifier: Modifier = Modifier,
    participation: Participation,
    student: Student?,
    participationDetailsViewModel: ParticipationDetailsViewModel,
    onStudentClicked: (Student) -> Unit,
) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    if (participationDetailsViewModel.selectedProjectStudents.value.isEmpty()) isSelected = false

    Row(
        modifier = modifier
            .clickable {
                if (student != null) {
                    onStudentClicked(student)
                    isSelected = !isSelected
                }
            }
            .background(
                if (isSelected) BlueMainLight25
                else Color.Transparent
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = student?.name ?: "Не найдено",
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = participation.priority.toString(),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = student?.group ?: "Не найдено",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ParticipationTableHead(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "ФИО",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentWidth()
        )
        Text(
            text = "Приоритет",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth()
        )
        Text(
            text = "Группа",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
    }
    Divider(thickness = 2.dp)
}

@Composable
fun ParticipationTable(
    modifier: Modifier = Modifier,
    viewModel: BaseGodViewModel,
    participationDetailsViewModel: ParticipationDetailsViewModel,
    onStudentSelected: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(10.dp)
            )
    ) {
        ParticipationTableHead(
            modifier = Modifier.fillMaxWidth()
        )

        val scrollState = rememberForeverLazyListState(KEY)
        val coroutineScope = rememberCoroutineScope()

        val participations = participationDetailsViewModel.projectParticipation.collectAsState()

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
            items(participations.value) { participation ->
                ParticipationTableItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    participation = participation,
                    student = viewModel.getStudentById(participation.studentId),
                    participationDetailsViewModel = participationDetailsViewModel,
                    onStudentClicked = { student ->
                        if (participationDetailsViewModel.selectedProjectStudents.value
                                .map { it.id }.contains(student.id)
                        ) {
                            println("${participationDetailsViewModel.selectedProjectStudents.value.map { it.id }} contains ${student.id}")
                            participationDetailsViewModel.selectedProjectStudents.value.remove(student)
                        } else {
                            participationDetailsViewModel.selectedProjectStudents.value.add(student)
                        }

                        onStudentSelected()
                    }
                )
                Divider()
            }
        }
    }
}
