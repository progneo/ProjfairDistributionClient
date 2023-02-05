package ui.preview.contract

import ru.student.distribution.core.base.mvi.UiIntent
import ru.student.distribution.core.base.mvi.UiState

class PreviewContract {

    sealed class Intent: UiIntent {
        object GetStudents: Intent()
        object GetProjects: Intent()
    }

    sealed class ScreenState: UiState {
        object Idle: ScreenState()
        object Loading: ScreenState()
        data class Data(val previewState: PreviewState): ScreenState()
    }

    sealed class PreviewState {
        object Loading: PreviewState()
        object Success: PreviewState()
    }
}