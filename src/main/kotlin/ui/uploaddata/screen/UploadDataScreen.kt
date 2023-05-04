package ui.uploaddata.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.theme.WhiteDark
import kotlinx.coroutines.launch
import navigation.NavController
import ui.uploaddata.viewmodel.DownloadType
import ui.uploaddata.viewmodel.UploadDataViewModel
import ui.uploaddata.widget.DownloadProgressDialog
import ui.uploaddata.widget.UpdateDataButton

//@Composable
//fun UploadDataScreen(
//    navController: NavController,
//    uploadDataViewModel: UploadDataViewModel,
//    id: Int,
//) {
//    UploadDataScreenView(navController, uploadDataViewModel)
//    when (val screenState = uploadDataViewModel.uiState.collectAsState().value) {
//        is UploadDataContract.ScreenState.Idle -> {
//        }
//
//        is UploadDataContract.ScreenState.Loading -> {
//
//        }
//
//        is UploadDataContract.ScreenState.SideEffect -> {
//            SideEffectHandler(
//                effectState = screenState.sideEffect
//            )
//        }
//
//        is UploadDataContract.ScreenState.Data -> {
//            UploadDataStateHandler(navController, screenState.uploadDataState, uploadDataViewModel)
//        }
//    }
//}
//
//@Composable
//fun SideEffectHandler(effectState: UploadDataContract.SideEffect) {
//    when (effectState) {
//        is UploadDataContract.SideEffect.ShowError -> {
//            println("ERROR SIDE EFFECT ${effectState.message}")
//        }
//    }
//}
//
//@Composable
//fun UploadDataStateHandler(
//    navController: NavController,
//    uploadDataState: UploadDataContract.UploadDataState,
//    uploadDataViewModel: UploadDataViewModel,
//) {
//    when (uploadDataState) {
//        is UploadDataContract.UploadDataState.Loading -> {
//            println("LOADING")
//        }
//
//        is UploadDataContract.UploadDataState.Error -> {
//            println("ERROR")
//        }
//
//        is UploadDataContract.UploadDataState.Success -> {
//            println("SUCCESS")
//            //UploadDataScreenView(navController, uploadDataViewModel)
//        }
//    }
//}

@Composable
fun UploadDataScreen(
    navController: NavController,
    uploadDataViewModel: UploadDataViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(WhiteDark)
    ) {
        var showDownloadProgress by remember { mutableStateOf(false) }

        var studentsDownloadProgress by remember { mutableStateOf(uploadDataViewModel.studentsDownloadFlow.value) }
        var projectsDownloadProgress by remember { mutableStateOf(uploadDataViewModel.projectsDownloadFlow.value) }
        var participationsDownloadProgress by remember { mutableStateOf(uploadDataViewModel.participationsDownloadFlow.value) }
        var institutesDownloadProgress by remember { mutableStateOf(uploadDataViewModel.institutesDownloadFlow.value) }
        var departmentsDownloadProgress by remember { mutableStateOf(uploadDataViewModel.departmentsDownloadFlow.value) }
        var supervisorsDownloadProgress by remember { mutableStateOf(uploadDataViewModel.supervisorsDownloadFlow.value) }

        val downloadProgressMap =
            mutableMapOf<DownloadType, Float>(
                DownloadType.STUDENTS to studentsDownloadProgress,
                DownloadType.PROJECTS to projectsDownloadProgress,
                DownloadType.PARTICIPATIONS to participationsDownloadProgress,
                DownloadType.INSTITUTES to institutesDownloadProgress,
                DownloadType.DEPARTMENTS to departmentsDownloadProgress,
                DownloadType.SUPERVISORS to supervisorsDownloadProgress,
            )

        rememberCoroutineScope().launch {
            uploadDataViewModel.studentsDownloadFlow.collect { downloadProgress ->
                studentsDownloadProgress = downloadProgress
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.projectsDownloadFlow.collect { downloadProgress ->
                projectsDownloadProgress = downloadProgress
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.participationsDownloadFlow.collect { downloadProgress ->
                participationsDownloadProgress = downloadProgress
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.institutesDownloadFlow.collect { downloadProgress ->
                institutesDownloadProgress = downloadProgress
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.departmentsDownloadFlow.collect { downloadProgress ->
                departmentsDownloadProgress = downloadProgress
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.supervisorsDownloadFlow.collect { downloadProgress ->
                supervisorsDownloadProgress = downloadProgress
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            UpdateDataButton(
                title = "Синхронизировать данные",
                icon = Icons.Rounded.Refresh,
                onClick = {
                    uploadDataViewModel.syncData()
                    showDownloadProgress = true
                }
            )
            UpdateDataButton(
                title = "Сбросить и загрузить данные",
                icon = Icons.Rounded.Clear,
                onClick = {
                    uploadDataViewModel.rebaseData()
                    showDownloadProgress = true
                }
            )
        }

        DownloadProgressDialog(
            showDownloadProgress,
            progressBars = downloadProgressMap,
            onDismissRequest = {
                showDownloadProgress = false
            }
        )
    }
}