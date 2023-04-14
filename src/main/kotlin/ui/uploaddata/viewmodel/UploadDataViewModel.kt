package ui.uploaddata.viewmodel

import base.mvi.BaseViewModel
import base.mvi.DataState
import domain.interactor.DownloadProgressInteractor
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import kotlinx.coroutines.launch
import ui.uploaddata.contract.UploadDataContract
import java.io.File

class UploadDataViewModel constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase,
    downloadProgressInteractor: DownloadProgressInteractor,
) : BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    val studentsDownloadFlow = downloadProgressInteractor.studentsDownloadFlow
    val projectsDownloadFlow = downloadProgressInteractor.projectsDownloadFlow
    val participationsDownloadFlow = downloadProgressInteractor.participationsDownloadFlow
    val institutesDownloadFlow = downloadProgressInteractor.institutesDownloadFlow
    val departmentsDownloadFlow = downloadProgressInteractor.departmentsDownloadFlow

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
        coroutineScope.launch {
            syncDataUseCase().collect {
                handleRequest(it)
            }
        }
    }

    private fun uploadExceptionalStudents(file: File) {
        coroutineScope.launch {
            uploadExceptionalStudentsUseCase(file).collect {
                handleRequest(it, file)
            }
        }
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
                println("LOADING")
                setState {
                    UploadDataContract.ScreenState.Data(
                        UploadDataContract.UploadDataState.Loading
                    )
                }
            }

            is DataState.Success -> {
                println("SUCCESS")
                if (it.data) {
                    println("setstate")
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

enum class DownloadType {
    STUDENTS,
    PROJECTS,
    PARTICIPATIONS,
    INSTITUTES,
    DEPARTMENTS
}