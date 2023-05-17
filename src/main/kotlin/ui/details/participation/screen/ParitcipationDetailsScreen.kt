package ui.details.participation.screen

import androidx.compose.animation.scaleOut
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
import domain.model.Student
import navigation.NavController
import ui.common.BaseGodViewModel
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import ui.details.participation.widget.ChooseParticipationTable
import ui.details.participation.widget.ParticipationTable
import ui.filter.FilterNode
import ui.filter.FilterType

@Composable
fun ParticipationDetailsScreen(
    navController: NavController,
    project: Project,
    viewModel: BaseGodViewModel,
    participationDetailsViewModel: ParticipationDetailsViewModel,
) {
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

    val projectParticipation = participationDetailsViewModel.projectParticipation.collectAsState()

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

    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                BackButton(
                    modifier = Modifier
                        .padding(16.dp),
                    navController = navController
                )
                Text(
                    text = project.name,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White),
            ) {
                Text(
                    text = "Изменить",
                    fontSize = 18.sp
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
                }
            )
            Column(
                modifier = Modifier.fillMaxWidth(2f / 24).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    enabled = isLeftTransferEnabled,
                    onClick = {
                        val newParts = mutableListOf<Participation>()
                        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
                        val selectedStudentIds = selectedProjectStudents.value.map { it.id }
                        pp.removeIf { part ->
                            part.studentId in selectedStudentIds
                        }

                        if (areStudentsOnRightSide) {
                            selectedProjectStudents.value.forEach { student ->
                                newParts.add(
                                    Participation(
                                        id = 0,
                                        studentId = student.id,
                                        studentNumz = student.numz,
                                        projectId = participationDetailsViewModel.currentProject!!.id,
                                        priority = 1
                                    )
                                )
                            }
                            val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
                            rp.addAll(newParts)
                            participationDetailsViewModel.setRequiredParticipation(rp)

                        } else if (areOutStudentsOnRightSide) {
                            val out = participationDetailsViewModel.outStudents.value.toMutableList()
                            out.addAll(selectedProjectStudents.value)
                            participationDetailsViewModel.setOutStudents(out)
                        }

                        participationDetailsViewModel.setProjectStudents(pp)
                        participationDetailsViewModel.clearSelectedProjectStudents()
                        updateActionsAvailability()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isLeftTransferEnabled) BlueMainLight else GrayLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
                Button(
                    enabled = isRightTransferEnabled,
                    onClick = {
                        val newParts = mutableListOf<Participation>()
                        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
                        val selectedStudentIds = selectedChooseStudents.value.map { it.id }
                        selectedChooseStudents.value.forEach { student ->
                            newParts.add(
                                Participation(
                                    id = 0,
                                    studentId = student.id,
                                    studentNumz = student.numz,
                                    projectId = project.id,
                                    priority = 1
                                )
                            )
                        }
                        pp.addAll(newParts)

                        if (areStudentsOnRightSide) {
                            val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
                            rp.removeIf { part ->
                                part.studentId in selectedStudentIds
                            }
                            participationDetailsViewModel.setRequiredParticipation(rp)
                        } else if (areOutStudentsOnRightSide) {
                            val out = participationDetailsViewModel.outStudents.value.toMutableList()
                            out.removeIf { stud ->
                                stud.id in selectedStudentIds
                            }
                            participationDetailsViewModel.setOutStudents(out)
                        }

                        participationDetailsViewModel.setProjectStudents(pp)
                        participationDetailsViewModel.clearSelectedChooseStudents()
                        updateActionsAvailability()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isRightTransferEnabled) BlueMainLight else GrayLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
                Button(
                    enabled = isBothTransferEnabled,
                    onClick = {
                        val newProjectParts = mutableListOf<Participation>()
                        val pp = participationDetailsViewModel.projectParticipation.value.toMutableList()
                        val selectedProjectStudentIds = selectedProjectStudents.value.map { it.id }
                        val selectedChooseStudentIds = selectedChooseStudents.value.map { it.id }
                        selectedChooseStudents.value.forEach { student ->
                            newProjectParts.add(
                                Participation(
                                    id = 0,
                                    studentId = student.id,
                                    studentNumz = student.numz,
                                    projectId = project.id,
                                    priority = 1
                                )
                            )
                        }
                        pp.removeIf { part ->
                            part.studentId in selectedProjectStudentIds
                        }
                        pp.addAll(newProjectParts)

                        if (areStudentsOnRightSide) {
                            val newChooseParts = mutableListOf<Participation>()
                            val rp = participationDetailsViewModel.requiredParticipation.value.toMutableList()
                            selectedProjectStudents.value.forEach { student ->
                                newChooseParts.add(
                                    Participation(
                                        id = 0,
                                        studentId = student.id,
                                        studentNumz = student.numz,
                                        projectId = participationDetailsViewModel.currentProject!!.id,
                                        priority = 1
                                    )
                                )
                            }
                            rp.removeIf { part ->
                                part.studentId in selectedChooseStudentIds
                            }
                            rp.addAll(newChooseParts)
                            participationDetailsViewModel.setRequiredParticipation(rp)
                        } else if (areOutStudentsOnRightSide) {
                            val out = participationDetailsViewModel.outStudents.value.toMutableList()
                            out.addAll(selectedProjectStudents.value)
                            out.removeIf { it.id in selectedChooseStudentIds }
                            participationDetailsViewModel.setOutStudents(out)
                        }

                        participationDetailsViewModel.setProjectStudents(pp)
                        participationDetailsViewModel.clearSelectedProjectStudents()
                        participationDetailsViewModel.clearSelectedChooseStudents()
                        updateActionsAvailability()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isBothTransferEnabled) BlueMainLight else GrayLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Octicons.ArrowSwitch24,
                        contentDescription = null
                    )
                }
                Button(
                    enabled = isLeftOutTransferEnabled,
                    onClick = {
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
                        participationDetailsViewModel.setProjectStudents(pp)
                        participationDetailsViewModel.setRequiredParticipation(rp)
                        participationDetailsViewModel.setOutStudents(out)
                        participationDetailsViewModel.clearSelectedProjectStudents()

                        updateActionsAvailability()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isLeftOutTransferEnabled) BlueMainLight else GrayLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Octicons.ArrowDownRight24,
                        contentDescription = null
                    )
                }
            }
            ChooseParticipationTable(
                modifier = Modifier.padding(24.dp),
                filterNode = FilterNode(
                    prev = null,
                    type = FilterType.INSTITUTE,
                    selectedValue = null,
                    next = FilterType.DEPARTMENT
                ),
                viewModel = viewModel,
                requiredParticipation = requiredParticipation.value,
                participationDetailsViewModel = participationDetailsViewModel,
                onNodeOpened = { areStudentsOnRight, areOuStudentsOnRight ->
                    areStudentsOnRightSide = areStudentsOnRight
                    areOutStudentsOnRightSide = areOuStudentsOnRight
                    updateActionsAvailability()
                },
                onItemSelected = {
                    updateActionsAvailability()
                }
            )
        }
    }
}