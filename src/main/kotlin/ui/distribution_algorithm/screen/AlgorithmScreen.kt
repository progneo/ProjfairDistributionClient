package ui.distribution_algorithm.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.compose.BorderedTitledComposable
import common.theme.BlueMainLight
import common.theme.WhiteDark
import navigation.NavController
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.distribution_algorithm.widget.AlgorithmDialog
import ui.distribution_algorithm.widget.EditableNumberField
import ui.distribution_algorithm.widget.LaunchButton

@Composable
fun AlgorithmScreen(
    navController: NavController,
    algorithmViewModel: AlgorithmViewModel,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    var lowerBoundary by remember {
        mutableStateOf(0)
    }
    var upperBoundary by remember {
        mutableStateOf(0)
    }

    var isUniform by remember {
        mutableStateOf(true)
    }
    var isByHigh by remember {
        mutableStateOf(false)
    }

    println("$lowerBoundary $upperBoundary")

    Box(
        modifier = Modifier.fillMaxSize().background(WhiteDark)
    ) {
        Column(
            modifier = Modifier
                .size(500.dp, Dp.Unspecified)
                .align(Alignment.Center)
        ) {
            BorderedTitledComposable(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                title = "Тип распределения"
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically).weight(8f),
                            text = "Равномерное распределение"
                        )
                        RadioButton(
                            modifier = Modifier.weight(2f),
                            selected = isUniform,
                            onClick = {
                                isUniform = true
                                isByHigh = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = BlueMainLight,
                                unselectedColor = BlueMainLight
                            )
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                    Row {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically).weight(8f),
                            text = "По максимальному заполнению проектов"
                        )
                        RadioButton(
                            modifier = Modifier.weight(2f),
                            selected = isByHigh,
                            onClick = {
                                isByHigh = true
                                isUniform = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = BlueMainLight,
                                unselectedColor = BlueMainLight
                            )
                        )
                    }
                }
            }
            Spacer(Modifier.size(32.dp))
            BorderedTitledComposable(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                title = "Количество мест"
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically).weight(8f),
                            text = "Верхняя граница"
                        )
                        EditableNumberField(
                            modifier = Modifier.weight(2f),
                            editableNumber = upperBoundary,
                            maxNumberCount = 2,
                            onDataChanged = {
                                val res = it.toIntOrNull() ?: 0
                                upperBoundary = if (res < lowerBoundary) {
                                    lowerBoundary
                                } else {
                                    res
                                }
                            }
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                    Row {
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically).weight(8f),
                            text = "Нижняя граница"
                        )
                        EditableNumberField(
                            modifier = Modifier.weight(2f),
                            maxNumberCount = 2,
                            editableNumber = lowerBoundary,
                            onDataChanged = {
                                val res = it.toIntOrNull() ?: 0
                                lowerBoundary = if (res > upperBoundary) {
                                    upperBoundary
                                }
                                else {
                                    res
                                }
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.size(32.dp))
            LaunchButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = upperBoundary != 0 && lowerBoundary != 0
            ) {
//            val distributionResults = DistributionLauncher(
//                students = algorithmViewModel.students.toMutableList(),
//                projects = algorithmViewModel.projects.toMutableList(),
//                participations = algorithmViewModel.participations.toMutableList(),
//                institutes = algorithmViewModel.institutes.toMutableList(),
//                distributionRule = DistributionRule(
//                    maxPlaces = 15,
//                    minPlaces = 9
//                ),
//                specialInstitute = algorithmViewModel.institutes.find { it.id == 0 }!!
//            ).launch()

//            distributionResults.institutesResults.forEach { instituteResults ->
//                ExportDataToExcel.writeProjectsWithStudents(
//                    students = algorithmViewModel.students.toList(),
//                    notApplied = instituteResults.notAppliedStudents,
//                    projects = instituteResults.projects,
//                    participations = instituteResults.participation,
//                    institute = instituteResults.institute,
//                    isUniformly = true,
//                    filePath = "E:/yadmin/"
//                )
//            }

//            algorithmViewModel.saveStudentsByProjects(
//                GeneratedDistribution(
//                    id = 0,
//                    results = distributionResults
//                )
//            )
            }
        }

        AlgorithmDialog(
            visible = showDialog,
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = {
                showDialog = false
            }
        )
    }
}

fun test(): Int {
    var counter = 0
    while (counter < 1_000_000_0) {
        println(counter++)
    }
    return counter
}