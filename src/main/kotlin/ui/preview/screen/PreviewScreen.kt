package ui.preview.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavController
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.ProjectTable
import ui.preview.widget.StudentTable
import ui.preview.widget.TabHome
import ui.preview.widget.TabPage.Projects
import ui.preview.widget.TabPage.Students

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(WhiteDark)
//    ) {
//
    var tabPage by remember { mutableStateOf(Students) }

    Scaffold(
        topBar = {
            TabHome(
                selectedTabIndex = tabPage.ordinal,
                onSelectedTab = {
                    tabPage = it
                }
            )
        }
    ) {
        val students = previewViewModel.students.collectAsState()
        val projects = previewViewModel.projects.collectAsState()
        when (tabPage) {
            Students -> {
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
    }

//        val students = previewViewModel.students.collectAsState()
//
//        TabLayout(
//            listOf(
//                TabItem.Students(students.value),
//                TabItem.Projects(listOf()),
//            )
//        )
//
//        StudentTable(
//            modifier = Modifier.padding(24.dp),
//            students = students.value
//        )
}
