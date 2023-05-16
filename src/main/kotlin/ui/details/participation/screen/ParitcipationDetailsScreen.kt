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
import domain.model.Project
import navigation.NavController
import ui.common.BaseGodViewModel
import ui.details.participation.widget.ChooseParticipationTable
import ui.details.participation.widget.ParticipationTable
import ui.filter.FilterNode
import ui.filter.FilterType

@Composable
fun ParticipationDetailsScreen(
    navController: NavController,
    project: Project,
    viewModel: BaseGodViewModel,
) {
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

    var isLeftItemSelected by remember {
        mutableStateOf(false)
    }

    var isRightItemSelected by remember {
        mutableStateOf(false)
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
                participations = viewModel.getParticipationByProject(project.id),
                viewModel = viewModel,
                onStudentSelected = { selectedStudents ->
                    isLeftItemSelected = selectedStudents.isNotEmpty()
                    isLeftTransferEnabled = isLeftItemSelected && areStudentsOnRightSide
                    isLeftOutTransferEnabled = isLeftItemSelected
                    isBothTransferEnabled = isLeftItemSelected && isRightItemSelected
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
                onItemSelected = { selectedStudents, areStudentsOnRight ->
                    isRightItemSelected = selectedStudents.isNotEmpty()
                    isRightTransferEnabled = areStudentsOnRight && isRightItemSelected
                    areStudentsOnRightSide = areStudentsOnRight
                    isBothTransferEnabled = isLeftItemSelected && isRightItemSelected
                    isLeftTransferEnabled = isLeftItemSelected && areStudentsOnRightSide
                    println(selectedStudents)
                }
            )
        }
    }
}