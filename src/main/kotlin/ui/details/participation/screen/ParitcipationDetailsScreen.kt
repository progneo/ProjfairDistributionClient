package ui.details.participation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.BackButton
import common.theme.BlueMainLight
import common.theme.GrayLight
import compose.icons.Octicons
import compose.icons.octicons.ArrowDownRight24
import compose.icons.octicons.ArrowSwitch24
import domain.model.Participation
import domain.model.Project
import navigation.NavController
import ui.common.BaseGodViewModel
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import ui.details.participation.widget.ChooseParticipationTable
import ui.details.participation.widget.ParticipationTable
import ui.filter.FilterNode
import ui.filter.FilterType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ParticipationDetailsScreen(
    navController: NavController,
    project: Project,
    viewModel: BaseGodViewModel,
    participationDetailsViewModel: ParticipationDetailsViewModel,
) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    ParticipationDetailsViewModel.projectId = project.id

    var isLeftTransferEnabled by remember {
        mutableStateOf(false)
    }

    var isRightTransferEnabled by remember {
        mutableStateOf(false)
    }

    var isBothTransferEnabled by remember {
        mutableStateOf(false)
    }

    var isLeftOutTransferEnabled by remember {
        mutableStateOf(false)
    }

    var areStudentsOnRightSide by remember {
        mutableStateOf(false)
    }

    var areOutStudentsOnRightSide by remember {
        mutableStateOf(false)
    }

    var isLeftItemSelected by remember {
        mutableStateOf(false)
    }

    var isRightItemSelected by remember {
        mutableStateOf(false)
    }

    val requiredParticipation = participationDetailsViewModel.requiredParticipation.collectAsState()

    val selectedProjectStudents = participationDetailsViewModel.selectedProjectStudents.collectAsState()
    val selectedChooseStudents = participationDetailsViewModel.selectedChooseStudents.collectAsState()

    fun updateActionsAvailability() {
        isLeftItemSelected = selectedProjectStudents.value.isNotEmpty()
        isRightItemSelected = selectedChooseStudents.value.isNotEmpty()
        isLeftTransferEnabled = isLeftItemSelected && (areStudentsOnRightSide || areOutStudentsOnRightSide)
        isRightTransferEnabled = isRightItemSelected && (areStudentsOnRightSide || areOutStudentsOnRightSide)
        isLeftOutTransferEnabled = isLeftItemSelected
        isBothTransferEnabled = isLeftItemSelected && isRightItemSelected
    }

    fun onDeleteParticipationsClick() {
        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
        val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
        val out = participationDetailsViewModel.outStudents.value.toMutableList()
        val selectedStudentIds = selectedProjectStudents.value.map { it.id }

        rp.removeIf { part ->
            part.studentId in selectedStudentIds
        }
        pp.removeIf { part ->
            part.studentId in selectedStudentIds
        }

        out.addAll(selectedProjectStudents.value)
        participationDetailsViewModel.setProjectParticipation(pp)
        participationDetailsViewModel.setRequiredParticipation(rp)
        participationDetailsViewModel.setOutStudents(out)
        participationDetailsViewModel.clearSelectedProjectStudents()

        updateActionsAvailability()
    }

    fun onTransferParticipationsToRightClick() {
        val newParts = mutableListOf<Participation>()
        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
        val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
        val selectedStudentIds = selectedProjectStudents.value.map { it.id }

        pp.removeIf { part ->
            part.studentId in selectedStudentIds
        }
        rp.removeIf { part ->
            part.studentId in selectedStudentIds
        }

        selectedProjectStudents.value.forEach { student ->
            val foundPart =
                participationDetailsViewModel.projectParticipation.value.find {
                    it.studentId == student.id && it.projectId == project.id
                }!!

            val tempParticipation =
                Participation(
                    id = foundPart.id,
                    studentId = student.id,
                    studentNumz = student.numz,
                    studentName = student.name,
                    projectId = participationDetailsViewModel.currentProject!!.id,
                    priority = 1,
                    updatedAt = dateFormat.format(Date()),
                    state = 2,
                )
            newParts.add(tempParticipation)
        }

        rp.addAll(newParts)
        participationDetailsViewModel.setRequiredParticipation(rp)
        participationDetailsViewModel.setProjectParticipation(pp)
    }

    fun onTransferParticipationsToLeftClick() {
        val newParts = mutableListOf<Participation>()
        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
        val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
        val selectedStudentIds = selectedChooseStudents.value.map { it.id }

        rp.removeIf { part ->
            part.studentId in selectedStudentIds
        }

        selectedChooseStudents.value.forEach { student ->
            val foundPart =
                participationDetailsViewModel.requiredParticipation.value.find {
                    it.studentId == student.id && it.projectId == participationDetailsViewModel.currentProject?.id
                }

            newParts.add(
                Participation(
                    id = foundPart?.id ?: 0,
                    studentId = student.id,
                    studentNumz = student.numz,
                    studentName = student.name,
                    projectId = project.id,
                    priority = 1,
                    updatedAt = dateFormat.format(Date()),
                    state = 2,
                ),
            )
        }
        pp.addAll(newParts)
        rp.addAll(newParts)

        if (areOutStudentsOnRightSide) {
            val out = participationDetailsViewModel.outStudents.value.toMutableList()
            out.removeIf { stud ->
                stud.id in selectedStudentIds
            }
            participationDetailsViewModel.setOutStudents(out)
        }

        participationDetailsViewModel.setRequiredParticipation(rp)
        participationDetailsViewModel.setProjectParticipation(pp)
    }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f),
            ) {
                BackButton(
                    modifier =
                        Modifier
                            .padding(16.dp),
                    navController = navController,
                )
                Text(
                    text = project.name,
                    fontSize = 32.sp,
                    modifier =
                        Modifier
                            .padding(16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Button(
                onClick = {
                    participationDetailsViewModel.updateParticipation()
                },
                modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White),
            ) {
                Text(
                    text = "Сохранить",
                    fontSize = 18.sp,
                )
            }
        }
        Row {
            ParticipationTable(
                modifier = Modifier.fillMaxWidth(0.475f).padding(24.dp),
                viewModel = viewModel,
                participationDetailsViewModel = participationDetailsViewModel,
                onStudentSelected = {
                    updateActionsAvailability()
                },
            )
            Column(
                modifier = Modifier.fillMaxWidth(2f / 24).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Button(
                    enabled = isLeftTransferEnabled,
                    onClick = {
                        if (areOutStudentsOnRightSide) {
                            onDeleteParticipationsClick()
                        } else {
                            onTransferParticipationsToRightClick()
                        }

                        participationDetailsViewModel.clearSelectedProjectStudents()
                        updateActionsAvailability()
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = if (isLeftTransferEnabled) BlueMainLight else GrayLight,
                            contentColor = Color.White,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
                Button(
                    enabled = isRightTransferEnabled,
                    onClick = {
                        onTransferParticipationsToLeftClick()
                        participationDetailsViewModel.clearSelectedChooseStudents()
                        updateActionsAvailability()
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = if (isRightTransferEnabled) BlueMainLight else GrayLight,
                            contentColor = Color.White,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                    )
                }
                Button(
                    enabled = isBothTransferEnabled,
                    onClick = {
                        onTransferParticipationsToLeftClick()
                        if (areStudentsOnRightSide) {
                            onTransferParticipationsToRightClick()
                        } else if (areOutStudentsOnRightSide) {
                            onDeleteParticipationsClick()
                        }

                        participationDetailsViewModel.clearSelectedProjectStudents()
                        participationDetailsViewModel.clearSelectedChooseStudents()
                        updateActionsAvailability()
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = if (isBothTransferEnabled) BlueMainLight else GrayLight,
                            contentColor = Color.White,
                        ),
                ) {
                    Icon(
                        imageVector = Octicons.ArrowSwitch24,
                        contentDescription = null,
                    )
                }
                Button(
                    enabled = isLeftOutTransferEnabled,
                    onClick = {
                        onDeleteParticipationsClick()
                        participationDetailsViewModel.clearSelectedProjectStudents()
                        updateActionsAvailability()
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = if (isLeftOutTransferEnabled) BlueMainLight else GrayLight,
                            contentColor = Color.White,
                        ),
                ) {
                    Icon(
                        imageVector = Octicons.ArrowDownRight24,
                        contentDescription = null,
                    )
                }
            }
            ChooseParticipationTable(
                modifier = Modifier.padding(24.dp),
                filterNode =
                    FilterNode(
                        prev = null,
                        type = FilterType.INSTITUTE,
                        selectedValue = null,
                        next = FilterType.DEPARTMENT,
                    ),
                viewModel = viewModel,
                requiredParticipation = requiredParticipation.value,
                participationDetailsViewModel = participationDetailsViewModel,
                onNodeOpened = { areStudentsOnRight, areOutStudentsOnRight ->
                    areStudentsOnRightSide = areStudentsOnRight
                    areOutStudentsOnRightSide = areOutStudentsOnRight
                    updateActionsAvailability()
                },
                onItemSelected = {
                    updateActionsAvailability()
                },
            )
        }
    }
}
