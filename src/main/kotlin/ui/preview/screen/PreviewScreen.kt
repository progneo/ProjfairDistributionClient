package ui.preview.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import navigation.NavController
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.*
import ui.preview.widget.PreviewTabPage.Projects
import ui.preview.widget.PreviewTabPage.Students
import ui.preview.widget.StudentsTabPage.Enrolled

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
) {
    var previewTabPage by remember { mutableStateOf(Students) }
    var studentsTabPage by remember { mutableStateOf(Enrolled) }

    Scaffold(
        topBar = {
            TabHome(
                modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified),
                selectedTabIndex = previewTabPage.ordinal,
                values = PreviewTabPage.values().toList(),
                onSelectedTab = {
                    previewTabPage = it as PreviewTabPage
                }
            )
        }
    ) {
        val students = previewViewModel.students.collectAsState()
        val projects = previewViewModel.projects.collectAsState()
        when (previewTabPage) {
            Students -> {
                Column {
                    Spacer(Modifier.size(16.dp))
                    TabHome(
                        modifier = Modifier.size(width = 600.dp, height = Dp.Unspecified),
                        selectedTabIndex = studentsTabPage.ordinal,
                        values = StudentsTabPage.values().toList(),
                        onSelectedTab = {
                            studentsTabPage = it as StudentsTabPage
                        }
                    )
                    Spacer(Modifier.size(16.dp))
                    StudentTable(
                        modifier = Modifier.padding(24.dp),
                        students = students.value,
                        previewViewModel,
                        navController
                    )
                }
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
