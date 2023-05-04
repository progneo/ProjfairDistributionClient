package ui.uploaddata.viewmodel

import base.mvi.BaseViewModel
import domain.interactor.DownloadProgressInteractor
import domain.usecase.uploaddata.RebaseDataUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import kotlinx.coroutines.launch
import ui.uploaddata.contract.UploadDataContract
import javax.inject.Inject

class UploadDataViewModel @Inject constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val rebaseDataUseCase: RebaseDataUseCase,
    downloadProgressInteractor: DownloadProgressInteractor,
) : BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    val studentsDownloadFlow = downloadProgressInteractor.studentsDownloadFlow
    val projectsDownloadFlow = downloadProgressInteractor.projectsDownloadFlow
    val participationsDownloadFlow = downloadProgressInteractor.participationsDownloadFlow
    val institutesDownloadFlow = downloadProgressInteractor.institutesDownloadFlow
    val departmentsDownloadFlow = downloadProgressInteractor.departmentsDownloadFlow
    val supervisorsDownloadFlow = downloadProgressInteractor.supervisorsDownloadFlow

    override fun createInitialState(): UploadDataContract.ScreenState {
        return UploadDataContract.ScreenState.Idle
    }

    override fun handleIntent(intent: UploadDataContract.Intent) {

    }

    fun syncData() {
        coroutineScope.launch {
            syncDataUseCase().collect {
                println("$it")
            }
        }
    }

    fun rebaseData() {
        println("rebase")
        coroutineScope.launch {
            rebaseDataUseCase().collect {
                println("$it")
            }
        }
    }
}

enum class DownloadType {
    STUDENTS,
    PROJECTS,
    PARTICIPATIONS,
    INSTITUTES,
    DEPARTMENTS,
    SUPERVISORS,
}