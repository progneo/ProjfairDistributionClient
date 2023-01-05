package ru.student.distribution

import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.student.distribution.data.remote.client.ProjectFairClient
import ru.student.distribution.ui.uploadfiles.UploadFilesScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 800.dp, height = 600.dp),
        title = "Medium"
    ) {
        rememberCoroutineScope().launch {
            withContext(Dispatchers.IO) {
                val response = ProjectFairClient.getClient().getProjects()
                println(response)
            }
        }

        UploadFilesScreen()

//        UploadFileCard(
//            fileTypeName = "Students",
//            onPickFileClicked = {
//                openFileDialog(
//                    window = window,
//                    title = "SHIT",
//                    allowedExtensions = listOf(".xlsx", ".xls"),
//                    allowMultiSelection = false
//                )
//            }
//        )
    }
}