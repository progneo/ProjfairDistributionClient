package ui.review.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.compose.BaseButton
import common.compose.RadioButtonGroupRow
import common.compose.Title
import navigation.NavController
import ui.filter.FilterConfigurationBlock
import ui.preview.widget.*
import ui.preview.widget.filter.ProjectFilterDialog
import ui.review.viewmodel.ReviewViewModel
import ui.review.widget.OthersDialog

@Composable
fun ReviewScreen(
    navController: NavController,
    reviewViewModel: ReviewViewModel
) {
    reviewViewModel.filterDepartments(null)

    var previewTabPage by remember { mutableStateOf(reviewViewModel.reviewTabPage.value) }
    var studentsTabPage by remember { mutableStateOf(StudentsTabPage.Enrolled) }

    var showFilter by remember { mutableStateOf(false) }
    var showOthers by remember { mutableStateOf(false) }

    fun studentTabPageToIndex(): Int {
        return when (studentsTabPage) {
            StudentsTabPage.Enrolled -> 0
            StudentsTabPage.Uncounted -> 1
        }
    }

    val students = reviewViewModel.getFilteredStudents(studentsTabPage).collectAsState()
    val projects = reviewViewModel.filteredProjects.collectAsState()

    val studentFilterConfiguration = reviewViewModel.studentFilterConfiguration.collectAsState()
    val projectFilterConfiguration = reviewViewModel.projectFilterConfiguration.collectAsState()

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
                                reviewViewModel.reviewTabPage.value = it as PreviewTabPage
                                previewTabPage = it
                            }
                        )

                        if (previewTabPage == PreviewTabPage.Students) {
                            RadioButtonGroupRow(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                titles = listOf(
                                    Title(StudentsTabPage.Enrolled.title, StudentsTabPage.Enrolled.name),
                                    Title(StudentsTabPage.Uncounted.title, StudentsTabPage.Uncounted.name)
                                ),
                                selected = studentTabPageToIndex()
                            ) {
                                studentsTabPage = StudentsTabPage.fromString(it.name!!)
                            }
                        }
                    }

                    Row {
                        when (previewTabPage) {
                            PreviewTabPage.Students -> {
                                FilterConfigurationBlock(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    studentFilterConfiguration.value
                                ) {
                                    showFilter = true
                                }
                            }

                            PreviewTabPage.Projects -> {
                                FilterConfigurationBlock(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    projectFilterConfiguration.value
                                ) {
                                    showFilter = true
                                }
                            }
                        }

                        Spacer(Modifier.size(32.dp))

                        BaseButton(
                            modifier = Modifier,
                            icon = Icons.Default.Menu,
                            onClick = {
                                showOthers = true
                            }
                        )
                    }
                }
            }
        },
    ) {
        when (previewTabPage) {
            PreviewTabPage.Students -> {
                Spacer(Modifier.size(16.dp))
                StudentTable(
                    modifier = Modifier.padding(24.dp),
                    students = students.value,
                    navController,
                    onStudentClicked = { student ->
                        reviewViewModel.getParticipationByStudent(student.id)
                    },
                    onProjectLinkClicked = { projectId ->
                        reviewViewModel.getProjectById(projectId)
                    }
                )
            }

            PreviewTabPage.Projects -> {
                ProjectTable(
                    modifier = Modifier.padding(24.dp),
                    projects = projects.value,
                    navController
                )
            }
        }

        ProjectFilterDialog(
            visible = showFilter,
            instituteFilterConfiguration = when (previewTabPage) {
                PreviewTabPage.Students -> studentFilterConfiguration.value.copy()
                PreviewTabPage.Projects -> projectFilterConfiguration.value.copy()
            },
            viewModel = reviewViewModel,
            onApplyClicked = { filterConfig ->
                when (previewTabPage) {
                    PreviewTabPage.Students -> {
                        reviewViewModel.studentFilterConfiguration.value = filterConfig.copy()
                    }

                    PreviewTabPage.Projects -> {
                        reviewViewModel.projectFilterConfiguration.value = filterConfig.copy()
                        reviewViewModel.filterProjects(filterConfig)
                    }
                }
            },
            onDismissRequest = {
                showFilter = false
            }
        )

        OthersDialog(
            visible = showOthers,
            onDismissRequest = {
                showOthers = false
            }
        )
    }
}