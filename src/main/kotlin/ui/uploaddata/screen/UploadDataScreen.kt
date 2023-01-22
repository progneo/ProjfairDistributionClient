package ui.uploaddata.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import common.file.openFileDialog
import navigation.NavController
import ui.uploaddata.contract.UploadDataContract
import ui.uploaddata.viewmodel.UploadDataViewModel
import ui.uploaddata.widget.UpdateDataButton
import ui.uploaddata.widget.UploadFileCard

@Composable
fun UploadDataScreen(
    navController: NavController,
    uploadDataViewModel: UploadDataViewModel,
    id: Int,
) {
    when (val screenState = uploadDataViewModel.uiState.collectAsState().value) {
        is UploadDataContract.ScreenState.Idle -> {
            uploadDataViewModel.setIntent(
                intent = UploadDataContract.Intent.SyncData
            )
        }

        is UploadDataContract.ScreenState.Loading -> {

        }

        is UploadDataContract.ScreenState.SideEffect -> {
            SideEffectHandler(
                effectState = screenState.sideEffect
            )
        }

        is UploadDataContract.ScreenState.Data -> {
            UploadDataStateHandler(
                navController = navController,
                uploadDataState = screenState.uploadDataState,
                uploadDataViewModel = uploadDataViewModel
            )
        }
    }
}

@Composable
fun SideEffectHandler(effectState: UploadDataContract.SideEffect) {
    when (effectState) {
        is UploadDataContract.SideEffect.ShowError -> {
            println("ERROR SIDE EFFECT")
        }
    }
}

@Composable
fun UploadDataStateHandler(
    navController: NavController,
    uploadDataState: UploadDataContract.UploadDataState,
    uploadDataViewModel: UploadDataViewModel,
) {
    when (uploadDataState) {
        is UploadDataContract.UploadDataState.Loading -> {
            println("LOADING")
        }

        is UploadDataContract.UploadDataState.Error -> {
            println("ERROR")
        }

        is UploadDataContract.UploadDataState.Success -> {
            println("SUCCESS")
            UploadDataScreenView(navController, uploadDataViewModel)
        }
    }
}

@Composable
fun UploadDataScreenView(
    navController: NavController,
    uploadDataViewModel: UploadDataViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        UploadFileCard(
            "Студенты-исключения",
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            val file = openFileDialog(
                window = ComposeWindow(),
                title = "SHIT",
                allowedExtensions = listOf(".xlsx", ".xls"),
                allowMultiSelection = false
            )

            uploadDataViewModel.setIntent(
                intent = UploadDataContract.Intent.UploadExceptionalStudents(file)
            )
        }
        UpdateDataButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
        ) {
            uploadDataViewModel.setIntent(
                intent = UploadDataContract.Intent.SyncData
            )
        }
    }
}