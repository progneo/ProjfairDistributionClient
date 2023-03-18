package ui.preview.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.compose.RadioButtonGroupRow
import common.compose.Title
import domain.Department
import domain.model.Institute
import navigation.NavController
import ui.filter.FilterConfigurationBlock
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.*
import ui.preview.widget.PreviewTabPage.Projects
import ui.preview.widget.PreviewTabPage.Students
import ui.preview.widget.StudentsTabPage.Enrolled
import ui.preview.widget.StudentsTabPage.Uncounted

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
) {
    var previewTabPage by remember { mutableStateOf(previewViewModel.previewTabPage.value) }
    var studentsTabPage by remember { mutableStateOf(Enrolled) }

    var showFilter by remember { mutableStateOf(false) }

    fun studentTabPageToIndex(): Int {
        return when (studentsTabPage) {
            Enrolled -> 0
            Uncounted -> 1
        }
    }

    val studentsWithParticipations = previewViewModel.studentsWithParticipations.collectAsState()
    val studentsWithoutParticipations = previewViewModel.studentsWithoutParticipations.collectAsState()
    val students = previewViewModel.students.collectAsState()
    val projects = previewViewModel.projects.collectAsState()

    var studentsToDisplay by remember { mutableStateOf(students) }

    var projectFilterConfiguration by remember {
        mutableStateOf(
            InstituteFilterConfiguration(
                institutes = listOf(
                    Institute(id = 0, name = "Институт информационных технологий и анализа данных"),
                    Institute(
                        id = 1,
                        name = "Институт информационных технологий и анализа данныхИнститут информационных технологий и анализа данных"
                    ),
                    Institute(id = 2, name = "third"),
                    Institute(id = 3, name = "fourth")
                ),
                departments = listOf(
                    Department(id = 0, name = "first"),
                    Department(id = 1, name = "second"),
                    Department(id = 2, name = "third")
                )
            )
        )
    }

    var studentFilterConfiguration by remember {
        mutableStateOf(
            InstituteFilterConfiguration(
                institutes = listOf(
                    Institute(id = 0, name = "Институт информационных технологий и анализа данных"),
                    Institute(
                        id = 1,
                        name = "Институт информационных технологий и анализа данныхИнститут информационных технологий и анализа данных"
                    ),
                    Institute(id = 2, name = "third"),
                    Institute(id = 3, name = "fourth")
                ),
                departments = listOf(
                    Department(id = 0, name = "first"),
                    Department(id = 1, name = "second"),
                    Department(id = 2, name = "third")
                )
            )
        )
    }

    val filterConfiguration: InstituteFilterConfiguration =
        if (previewTabPage == Students) studentFilterConfiguration else projectFilterConfiguration

    Scaffold(
        topBar = {
            Box(Modifier.padding(top = 16.dp, end = 24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row {
                        TabHome(
                            modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified),
                            selectedTabIndex = previewTabPage.ordinal,
                            values = PreviewTabPage.values().toList(),
                            onSelectedTab = {
                                previewViewModel.previewTabPage.value = it as PreviewTabPage
                                previewTabPage = it
                            }
                        )

                        if (previewTabPage == Students) {
                            RadioButtonGroupRow(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                titles = listOf(
                                    Title(Enrolled.title, Enrolled.name),
                                    Title(Uncounted.title, Uncounted.name)
                                ),
                                selected = studentTabPageToIndex()
                            ) {
                                studentsTabPage = StudentsTabPage.fromString(it.name!!)

                                studentsToDisplay = when (studentsTabPage) {
                                    Enrolled -> {
                                        studentsWithParticipations
                                    }

                                    Uncounted -> {
                                        studentsWithoutParticipations
                                    }
                                }
                            }
                        }
                    }

                    FilterConfigurationBlock(
                        filterConfiguration
                    ) {
                        showFilter = true
                    }
                }
            }
        },
    ) {
        when (previewTabPage) {
            Students -> {
                Spacer(Modifier.size(16.dp))
                StudentTable(
                    modifier = Modifier.padding(24.dp),
                    students = studentsToDisplay.value,
                    previewViewModel,
                    navController
                )
            }

            Projects -> {
                ProjectTable(
                    modifier = Modifier.padding(24.dp),
                    projects = projects.value,
                    previewViewModel,
                    navController
                )
            }
        }

        ProjectFilterDialog(
            visible = showFilter,
            instituteFilterConfiguration = filterConfiguration,
            onApplyClicked = { filterConfig ->
                when (previewTabPage) {
                    Students -> {
                        studentFilterConfiguration = filterConfig
                    }

                    Projects -> {
                        projectFilterConfiguration = filterConfig
                    }
                }
            },
            onDismissRequest = {
                println(filterConfiguration.filters)
                showFilter = false
            }
        )
    }
}