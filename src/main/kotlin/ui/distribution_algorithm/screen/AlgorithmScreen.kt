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
import domain.model.GeneratedDistribution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import navigation.NavController
import ru.student.distribution.domain.distribution.DistributionLauncher
import ru.student.distribution.domain.distribution.DistributionRule
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.distribution_algorithm.widget.AlgorithmDialog
import ui.distribution_algorithm.widget.EditableNumberField
import ui.distribution_algorithm.widget.LaunchButton

@Composable
fun AlgorithmScreen(
    navController: NavController,
    algorithmViewModel: AlgorithmViewModel,
) {
    var showLoading by remember {
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
                                upperBoundary = it.toIntOrNull() ?: 0
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
                                lowerBoundary = it.toIntOrNull() ?: 0
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

                coroutineScope.launch(Dispatchers.Default) {
                    showLoading = true
                    val distributionResults = DistributionLauncher(
                        students = algorithmViewModel.students.value.toMutableList(),
                        projects = algorithmViewModel.projects.value.toMutableList(),
                        participations = algorithmViewModel.participations.value.toMutableList(),
                        institutes = algorithmViewModel.institutes.value.toMutableList(),
                        distributionRule = DistributionRule(
                            maxPlaces = 15,
                            minPlaces = 9
                        ),
                        specialInstitute = algorithmViewModel.institutes.value.find { it.id == 0 }!!
                    ).launch()
//                    algorithmViewModel.saveStudentsByProjects(
//                        GeneratedDistribution(
//                            id = 0,
//                            results = distributionResults
//                        )
//                    )
                    showLoading = false
                }
            }
        }

        AlgorithmDialog(
            visible = showLoading,
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = {
                showLoading = false
            }
        )
    }
}