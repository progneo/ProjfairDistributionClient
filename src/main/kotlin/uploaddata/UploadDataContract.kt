package ru.student.distribution.ui.uploaddata

import ru.student.distribution.core.base.mvi.UiIntent
import ru.student.distribution.core.base.mvi.UiState

class UploadDataContract {

    sealed class Intent: UiIntent {
        object SyncData: Intent()
        object UploadExceptionalStudents: Intent()
    }

    sealed class ScreenState: UiState {
        object Idle: ScreenState()
        object Loading: ScreenState()
        data class Data(val uploadDataState: UploadDataState): ScreenState()
        data class SideEffect(val sideEffect: UploadDataContract.SideEffect): ScreenState()
    }

    sealed class UploadDataState {
        object Loading: UploadDataState()
        object Success: UploadDataState()
        data class Error(val errorMessage: String?): UploadDataState()
        //data class Empty(val emptyMessage: String?): UploadDataState()
    }

    sealed class SideEffect  {
        data class ShowError(val message : String?) : SideEffect()
    }
}