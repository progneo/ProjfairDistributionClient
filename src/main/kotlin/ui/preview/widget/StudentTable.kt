package ui.preview.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import common.compose.rememberForeverLazyListState
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import common.theme.GrayLight
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.SortAlphaDown
import compose.icons.tablericons.ArrowsSort
import domain.model.Participation
import domain.model.Project
import domain.model.Student
import kotlinx.coroutines.launch
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute

private const val KEY = "PREVIEW_STUDENTS"

@Composable
fun StudentTableItem(
    modifier: Modifier = Modifier,
    student: Student,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = student.name,
            modifier = Modifier
                .fillMaxWidth(0.6f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,

            )
        Text(
            text = student.numz.toString(),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = student.group,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StudentTableHead(
    modifier: Modifier = Modifier,
    specialtySelected: Boolean,
    onSpecialtyClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(GrayLight)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "ФИО",
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .wrapContentWidth()
        )
        Text(
            text = "Номер з.к.",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSpecialtyClicked()
                }
                .wrapContentWidth()
        ) {
            Text(
                text = "Группа",
                modifier = Modifier
                    .wrapContentWidth()
            )
            Spacer(Modifier.size(8.dp))
            Icon(
                modifier = Modifier.size(20.dp, 20.dp),
                imageVector = if (specialtySelected) FontAwesomeIcons.Solid.SortAlphaDown else TablerIcons.ArrowsSort,
                contentDescription = null,
                tint = BlueMainDark
            )
        }
    }
    Divider(thickness = 2.dp)
}

@Composable
fun StudentTable(
    modifier: Modifier = Modifier,
    students: List<Student>,
    specialtySelected: Boolean,
    navController: NavController,
    onStudentClicked: (Student) -> List<Participation>,
    onProjectLinkClicked: (Int) -> Unit,
    onSpecialtyClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, BlueMainLight),
                RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
    ) {
        StudentTableHead(
            modifier = Modifier.fillMaxWidth(),
            specialtySelected = specialtySelected,
            onSpecialtyClicked = onSpecialtyClicked
        )

        val scrollState = rememberForeverLazyListState(KEY)
        val coroutineScope = rememberCoroutineScope()

        var studentParticipations by remember {
            mutableStateOf<List<Participation>>(emptyList())
        }
        var studentState by remember { mutableStateOf<Student?>(null) }
        var showPopUp by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                items(students) { student ->
                    StudentTableItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                studentParticipations = onStudentClicked(student)
                                studentState = student
                                showPopUp = true
                            },
                        student = student
                    )
                    Divider()
                }
            }

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = scrollState
                ),
                style = ScrollbarStyle(
                    minimalHeight = LocalScrollbarStyle.current.minimalHeight,
                    thickness = 16.dp,
                    shape = LocalScrollbarStyle.current.shape,
                    hoverDurationMillis = LocalScrollbarStyle.current.hoverDurationMillis,
                    unhoverColor = LocalScrollbarStyle.current.unhoverColor,
                    hoverColor = LocalScrollbarStyle.current.hoverColor
                )
            )
        }

        StudentParticipationsDialog(
            visible = showPopUp,
            title = studentState?.name.toString(),
            subtitle = studentState?.numz.toString(),
            items = studentParticipations,
            onDismissRequest = {
                showPopUp = false
            },
            onProjectLinkClicked = { projectId ->
                onProjectLinkClicked(projectId)
            }
        )
    }
}

