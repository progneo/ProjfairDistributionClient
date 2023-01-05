package ru.student.distribution

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.student.distribution.data.remote.client.ProjectFairClient
import ru.student.distribution.data.repository.UploadDataRepositoryImpl
import ru.student.distribution.domain.usecase.uploaddata.SyncDataUseCase
import ru.student.distribution.ui.uploaddata.UploadDataViewModel
import ru.student.distribution.ui.uploaddata.UploadFilesScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 800.dp, height = 600.dp),
        title = "Medium"
    ) {
//        rememberCoroutineScope().launch {
//            withContext(Dispatchers.IO) {
//                val response = ProjectFairClient.getClient().getProjects()
//                //println(response)
//            }
//        }

        UploadFilesScreen(
            uploadDataViewModel = UploadDataViewModel(
                syncDataUseCase = SyncDataUseCase(
                    uploadDataRepository = UploadDataRepositoryImpl(
                        ioDispatcher = Dispatchers.IO
                    )
                )
            )
        )

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