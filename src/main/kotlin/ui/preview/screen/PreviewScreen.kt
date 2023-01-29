package ui.preview.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.theme.WhiteDark
import navigation.NavController
import ui.preview.viewmodel.PreviewViewModel
import ui.preview.widget.StudentTable

@Composable
fun PreviewScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteDark)
    ) {
        val students = previewViewModel.students.collectAsState()
        StudentTable(
            modifier = Modifier.padding(24.dp),
            students = students.value
        )
    }
}