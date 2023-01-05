package ru.student.distribution.ui.uploaddata

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun UploadFilesScreen(
    uploadDataViewModel: UploadDataViewModel
) {
    when (val screenState = uploadDataViewModel.uiState.collectAsState().value) {
        is UploadDataContract.ScreenState.Idle -> {
            println("SCREEN IDLE")
            uploadDataViewModel.setIntent(
                intent = UploadDataContract.Intent.SyncData
            )
        }
        is UploadDataContract.ScreenState.Loading -> {
            println("SCREEN LOADING")
        }
        is UploadDataContract.ScreenState.SideEffect -> {
            SideEffectHandler(
                effectState = screenState.sideEffect
            )
        }
        is UploadDataContract.ScreenState.Data -> {
            UploadDataStateHandler(
                uploadDataState = screenState.uploadDataState,
                uploadDataViewModel = uploadDataViewModel
            )
        }
    }
}

@Composable
fun UploadFilesScreenView(
    uploadDataViewModel: UploadDataViewModel
) {
    //uploadDataViewModel.setIntent(UploadDataContract.Intent.SyncData)
    Box {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            UploadFileCard("Students", {})
            UploadFileCard("Teachers", {})
            UpdateFilesButton()
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
    uploadDataState: UploadDataContract.UploadDataState,
    uploadDataViewModel: UploadDataViewModel
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
            UploadFilesScreenView(uploadDataViewModel)
        }
    }
}