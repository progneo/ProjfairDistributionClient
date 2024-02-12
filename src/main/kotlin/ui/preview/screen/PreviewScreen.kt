package ui.preview.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import common.compose.BaseButton
import common.compose.RadioButtonGroupRow
import common.compose.Title
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ClipboardList
import kotlinx.coroutines.launch
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import ui.details.project.widget.EditableSearchField
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

    val students = previewViewModel.getFilteredStudents(studentsTabPage).collectAsState()
    val projects = previewViewModel.filteredProjects.collectAsState()
    val logs = previewViewModel.logs.collectAsState()

    rememberCoroutineScope().launch {
        previewViewModel.searchStudentString.collect {
            previewViewModel.filterStudentsByString(it)
        }
    }

    rememberCoroutineScope().launch {
        previewViewModel.searchProjectString.collect {
            previewViewModel.filterProjectsByString(it)
        }
    }

    var showFilter by remember { mutableStateOf(false) }
    var showLogging by remember { mutableStateOf(false) }

    fun studentTabPageToIndex(): Int {
        return when (studentsTabPage) {
            Enrolled -> 0
            Uncounted -> 1
        }
    }

    val specialtySelected =
        remember {
            mutableStateOf(false)
        }

    val instituteSelected =
        remember {
            mutableStateOf(false)
        }

    val studentFilterConfiguration = previewViewModel.studentFilterConfiguration.collectAsState()
    val projectFilterConfiguration = previewViewModel.projectFilterConfiguration.collectAsState()

    Scaffold(
        topBar = {
            Box(Modifier.padding(top = 16.dp, end = 24.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                ) {
                    Row {
                        TabHome(
                            modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified),
                            selectedTabIndex = previewTabPage.ordinal,
                            values = PreviewTabPage.values().toList(),
                            onSelectedTab = {
                                previewViewModel.previewTabPage.value = it as PreviewTabPage
                                previewTabPage = it
                            },
                        )

                        if (previewTabPage == Students) {
                            RadioButtonGroupRow(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                titles =
                                    listOf(
                                        Title(Enrolled.title, Enrolled.name),
                                        Title(Uncounted.title, Uncounted.name),
                                    ),
                                selected = studentTabPageToIndex(),
                            ) {
                                studentsTabPage = StudentsTabPage.fromString(it.name!!)
                            }
                        }
                    }

                    when (previewTabPage) {
                        Students -> {
                            Row {
                                FilterConfigurationBlock(
                                    modifier =
                                        Modifier
                                            .align(Alignment.CenterVertically),
                                    studentFilterConfiguration.value,
                                ) {
                                    showFilter = true
                                }
                                Spacer(Modifier.size(24.dp))
                                EditableSearchField(
                                    modifier = Modifier.size(width = 300.dp, height = Dp.Unspecified),
                                    text = previewViewModel.lastSearchStudentString.collectAsState().value,
                                    content = "Поиск",
                                    onDataChanged = { searchString ->
                                        previewViewModel.lastSearchStudentString.value = searchString
                                    },
                                )
                                Spacer(Modifier.size(24.dp))
                                BaseButton(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    icon = FontAwesomeIcons.Solid.ClipboardList,
                                    requiredSize = DpSize(50.dp, 50.dp),
                                ) {
                                    showLogging = true
                                }
                            }
                        }

                        Projects -> {
                            Row {
                                FilterConfigurationBlock(
                                    modifier =
                                        Modifier
                                            .align(Alignment.CenterVertically),
                                    projectFilterConfiguration.value,
                                ) {
                                    showFilter = true
                                }
                                Spacer(Modifier.size(24.dp))
                                EditableSearchField(
                                    modifier = Modifier.size(width = 300.dp, height = Dp.Unspecified),
                                    text = previewViewModel.lastSearchProjectString.collectAsState().value,
                                    content = "Поиск",
                                    onDataChanged = { searchString ->
                                        previewViewModel.lastSearchProjectString.value = searchString
                                    },
                                )
                                Spacer(Modifier.size(24.dp))
                                BaseButton(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    icon = FontAwesomeIcons.Solid.ClipboardList,
                                    requiredSize = DpSize(50.dp, 50.dp),
                                ) {
                                    showLogging = true
                                }
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
                    specialtySelected = specialtySelected.value,
                    navController,
                    onStudentClicked = { student ->
                        previewViewModel.getParticipationByStudent(student.id)
                    },
                    onProjectLinkClicked = { projectId ->
                        val project = previewViewModel.getProjectById(projectId)

                        if (project != null) {
                            val bundle =
                                Bundle().apply {
                                    put("project", project)
                                }
                            navController.navigate(ScreenRoute.PROJECT_DETAILS, bundle, previewViewModel)
                        }
                    },
                    onSpecialtyClicked = {
                        specialtySelected.value = !specialtySelected.value
                        previewViewModel.filterStudentsBySpecialty(specialtySelected.value)
                    },
                )
            }

            Projects -> {
                ProjectTable(
                    modifier = Modifier.padding(24.dp),
                    projects = projects.value,
                    navController = navController,
                    baseGodViewModel = previewViewModel,
                    instituteSelected = instituteSelected.value,
                    isReview = false,
                    onInstituteClicked = {
                        instituteSelected.value = !instituteSelected.value
                        previewViewModel.filterProjectsByInstitute(instituteSelected.value)
                    },
                )
            }
        }

        ProjectFilterDialog(
            visible = showFilter,
            instituteFilterConfiguration =
                when (previewTabPage) {
                    Students -> studentFilterConfiguration.value.copy()
                    Projects -> projectFilterConfiguration.value.copy()
                },
            viewModel = previewViewModel,
            onApplyClicked = { filterConfig ->
                when (previewTabPage) {
                    Students -> {
                        previewViewModel.studentFilterConfiguration.value = filterConfig.copy()
                        previewViewModel.filterStudents(filterConfig)
                    }

                    Projects -> {
                        previewViewModel.projectFilterConfiguration.value = filterConfig.copy()
                        previewViewModel.filterProjects(filterConfig)
                    }
                }
            },
            onDismissRequest = {
                showFilter = false
            },
        )

        LoggingDialog(
            visible = showLogging,
            items = logs.value,
            students = previewViewModel.getAllStudents(),
            projects = previewViewModel.getAllProjectsFlow().value,
            onDismissRequest = {
                showLogging = false
            },
        )
    }
}
