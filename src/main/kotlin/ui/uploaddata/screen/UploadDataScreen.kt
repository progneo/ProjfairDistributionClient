package ui.uploaddata.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.theme.WhiteDark
import kotlinx.coroutines.launch
import navigation.NavController
import ui.uploaddata.viewmodel.DownloadType
import ui.uploaddata.viewmodel.UploadDataViewModel
import ui.uploaddata.widget.DownloadProgressDialog
import ui.uploaddata.widget.UpdateDataButton

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
            mapOf(
                //mutable types
                DownloadType.STUDENTS to studentsDownloadProgress,
                DownloadType.PROJECTS to projectsDownloadProgress,
                DownloadType.PARTICIPATIONS to participationsDownloadProgress,
                //immutable types
                DownloadType.INSTITUTES to institutesDownloadProgress,
                DownloadType.DEPARTMENTS to departmentsDownloadProgress,
                DownloadType.SUPERVISORS to supervisorsDownloadProgress,
            )

        val downloadProgressStateMap = remember {
            mutableStateMapOf<DownloadType, Float>()
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.studentsDownloadFlow.collect { downloadProgress ->
                studentsDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.STUDENTS] != null) {
                    downloadProgressStateMap[DownloadType.STUDENTS] = downloadProgress
                }
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.projectsDownloadFlow.collect { downloadProgress ->
                projectsDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.PROJECTS] != null) {
                    downloadProgressStateMap[DownloadType.PROJECTS] = downloadProgress
                }
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.participationsDownloadFlow.collect { downloadProgress ->
                participationsDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.PARTICIPATIONS] != null) {
                    downloadProgressStateMap[DownloadType.PARTICIPATIONS] = downloadProgress
                }
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.institutesDownloadFlow.collect { downloadProgress ->
                institutesDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.INSTITUTES] != null) {
                    downloadProgressStateMap[DownloadType.INSTITUTES] = downloadProgress
                }
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.departmentsDownloadFlow.collect { downloadProgress ->
                departmentsDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.DEPARTMENTS] != null) {
                    downloadProgressStateMap[DownloadType.DEPARTMENTS] = downloadProgress
                }
            }
        }

        rememberCoroutineScope().launch {
            uploadDataViewModel.supervisorsDownloadFlow.collect { downloadProgress ->
                supervisorsDownloadProgress = downloadProgress
                if (downloadProgressStateMap[DownloadType.SUPERVISORS] != null) {
                    downloadProgressStateMap[DownloadType.SUPERVISORS] = downloadProgress
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .size(width = 800.dp, height = Dp.Unspecified)
                .padding(64.dp)
        ) {
            downloadProgressMap.forEach { (key, value) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = key.title
                    )
                    if (key.isMutable) {
                        Spacer(Modifier.size(32.dp))
                        UpdateDataButton(
                            modifier = Modifier.weight(4f),
                            title = "Синхронизировать данные",
                            icon = Icons.Rounded.Refresh,
                            contentPadding = PaddingValues(4.dp),
                            onClick = {
                                downloadProgressStateMap.clear()
                                uploadDataViewModel.syncByDownloadType(key)
                                downloadProgressStateMap[key] = value
                                showDownloadProgress = true
                            }
                        )
                    }
                    Spacer(Modifier.size(32.dp))
                    UpdateDataButton(
                        modifier = Modifier.weight(if (key.isMutable) 4f else 8f),
                        title = "Сбросить и загрузить",
                        icon = Icons.Rounded.Clear,
                        contentPadding = PaddingValues(4.dp),
                        onClick = {
                            downloadProgressStateMap.clear()
                            uploadDataViewModel.rebaseByDownloadType(key)
                            downloadProgressStateMap[key] = value
                            println(downloadProgressStateMap.toMap())
                            showDownloadProgress = true
                        }
                    )
                }
                Spacer(Modifier.size(16.dp))
            }
            Spacer(Modifier.size(32.dp))
            UpdateDataButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Синхронизировать данные",
                icon = Icons.Rounded.Refresh,
                onClick = {
                    downloadProgressStateMap.clear()
                    uploadDataViewModel.syncData()
                    showDownloadProgress = true
                }
            )
            Spacer(Modifier.size(32.dp))
            UpdateDataButton(
                modifier = Modifier.fillMaxWidth(),
                title = "Сбросить и загрузить данные",
                icon = Icons.Rounded.Clear,
                onClick = {
                    downloadProgressStateMap.clear()
                    uploadDataViewModel.rebaseData()
                    showDownloadProgress = true
                }
            )
        }

        DownloadProgressDialog(
            showDownloadProgress,
            progressBars = if (downloadProgressStateMap.isEmpty()) downloadProgressMap else downloadProgressStateMap,
            onDismissRequest = {
                uploadDataViewModel.cancelOperations()
                showDownloadProgress = false
            }
        )
    }
}