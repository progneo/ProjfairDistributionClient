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
import navigation.NavController
import ui.filter.FilterConfigurationBlock
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.*
import ui.preview.widget.PreviewTabPage.Projects
import ui.preview.widget.PreviewTabPage.Students
import ui.preview.widget.StudentsTabPage.Enrolled
import ui.preview.widget.StudentsTabPage.Uncounted
import ui.preview.widget.filter.ProjectFilterDialog

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
) {
    previewViewModel.filterDepartments(null)

    var previewTabPage by remember { mutableStateOf(previewViewModel.previewTabPage.value) }
    var studentsTabPage by remember { mutableStateOf(Enrolled) }

    var showFilter by remember { mutableStateOf(false) }

    fun studentTabPageToIndex(): Int {
        return when (studentsTabPage) {
            Enrolled -> 0
            Uncounted -> 1
        }
    }

    val students = previewViewModel.getFilteredStudents(studentsTabPage).collectAsState()
    val projects = previewViewModel.filteredProjects.collectAsState()

    val studentFilterConfiguration = previewViewModel.studentFilterConfiguration.collectAsState()
    val projectFilterConfiguration = previewViewModel.projectFilterConfiguration.collectAsState()

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
                            }
                        }
                    }

                    when (previewTabPage) {
                        Students -> {
                            FilterConfigurationBlock(
                                studentFilterConfiguration.value
                            ) {
                                showFilter = true
                            }
                        }

                        Projects -> {
                            FilterConfigurationBlock(
                                projectFilterConfiguration.value
                            ) {
                                showFilter = true
                            }
                        }
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
                    students = students.value,
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
            instituteFilterConfiguration = when (previewTabPage) {
                Students -> studentFilterConfiguration.value.copy()
                Projects -> projectFilterConfiguration.value.copy()
            },
            previewViewModel = previewViewModel,
            onApplyClicked = { filterConfig ->
                when (previewTabPage) {
                    Students -> {
                        previewViewModel.studentFilterConfiguration.value = filterConfig.copy()
                    }

                    Projects -> {
                        previewViewModel.projectFilterConfiguration.value = filterConfig.copy()
                        previewViewModel.filterProjects(filterConfig)
                    }
                }
            },
            onDismissRequest = {
                showFilter = false
            }
        )
    }
}