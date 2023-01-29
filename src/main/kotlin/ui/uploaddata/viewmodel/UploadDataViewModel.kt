package ui.uploaddata.viewmodel

import base.mvi.DataState
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.student.distribution.core.base.mvi.BaseViewModel
import ui.uploaddata.contract.UploadDataContract
import java.io.File
import javax.inject.Inject

class UploadDataViewModel @Inject constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase
) : BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    override fun createInitialState(): UploadDataContract.ScreenState {
        return UploadDataContract.ScreenState.Idle
    }

    override fun handleIntent(intent: UploadDataContract.Intent) {
        when (intent) {
            is UploadDataContract.Intent.SyncData -> syncData()
            is UploadDataContract.Intent.UploadExceptionalStudents -> uploadExceptionalStudents(intent.file)
        }
    }

    private fun syncData() {
        syncDataUseCase().onEach {
            handleRequest(it)
        }.launchIn(coroutineScope)
    }

    private fun uploadExceptionalStudents(file: File) {
        uploadExceptionalStudentsUseCase(file).onEach {
            handleRequest(it, file)
        }.launchIn(coroutineScope)
    }

    private fun handleRequest(it: DataState<Boolean>, file: File? = null) {
        when (it) {
            is DataState.Error -> {
                setState {
                    UploadDataContract.ScreenState.SideEffect(
                        UploadDataContract.SideEffect.ShowError(
                            "cannot LOAD data from ${if (file == null) "server" else "database"}"
                        )
                    )
                }
            }

            is DataState.Loading -> {
                setState {
                    UploadDataContract.ScreenState.Data(
                        UploadDataContract.UploadDataState.Loading
                    )
                }
            }

            is DataState.Success -> {
                if (it.data) {
                    setState {
                        UploadDataContract.ScreenState.Data(
                            UploadDataContract.UploadDataState.Success
                        )
                    }
                } else {
                    setState {
                        UploadDataContract.ScreenState.SideEffect(
                            UploadDataContract.SideEffect.ShowError(
                                "cannot SAVE data to ${if (file == null) "server" else "database"}"
                            )
                        )
                    }
                }
            }
        }
    }
}