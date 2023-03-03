package ui.preview.screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.model.Student
import navigation.NavController
import ui.details.project.widget.RadioButtonGroupRow
import ui.details.project.widget.Title
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.*
import ui.preview.widget.PreviewTabPage.Projects
import ui.preview.widget.PreviewTabPage.Students
import ui.preview.widget.StudentsTabPage.*

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
) {
    var previewTabPage by remember { mutableStateOf(Students) }
    var studentsTabPage by remember { mutableStateOf(Enrolled) }

    val students = previewViewModel.students.collectAsState()
    val projects = previewViewModel.projects.collectAsState()

    var studentsToDisplay by remember { mutableStateOf(students) }

    Scaffold(
        topBar = {
            Row {
                TabHome(
                    modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified),
                    selectedTabIndex = previewTabPage.ordinal,
                    values = PreviewTabPage.values().toList(),
                    onSelectedTab = {
                        previewTabPage = it as PreviewTabPage
                    }
                )

                if (previewTabPage == Students) {
                    RadioButtonGroupRow(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        titles = listOf(
                            Title("С заявками", Enrolled.name),
                            Title("Без заявок", Uncounted.name)
                        ),
                        selected = 0
                    ) {
                        studentsTabPage = StudentsTabPage.fromString(it.name!!)

                        studentsToDisplay = when (studentsTabPage) {
                            Enrolled -> {
                                students
                            }
                            Uncounted -> {
                                //TODO: give students without participations here
                                students
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
    }
}
