package ui.uploaddata.viewmodel

import base.mvi.BaseViewModel
import domain.interactor.DownloadProgressInteractor
import domain.usecase.department.UploadDepartmentsUseCase
import domain.usecase.institute.UploadInstitutesUseCase
import domain.usecase.participation.RebaseParticipationUseCase
import domain.usecase.participation.SyncParticipationUseCase
import domain.usecase.project.RebaseProjectsUseCase
import domain.usecase.project.SyncProjectUseCase
import domain.usecase.project.SyncProjectsUseCase
import domain.usecase.student.RebaseStudentsUseCase
import domain.usecase.student.SyncStudentsUseCase
import domain.usecase.supervisor.UploadSupervisorsUseCase
import domain.usecase.uploaddata.CancelOperationsUseCase
import domain.usecase.uploaddata.RebaseDataUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.uploaddata.contract.UploadDataContract
import javax.inject.Inject

class UploadDataViewModel @Inject constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val rebaseDataUseCase: RebaseDataUseCase,
    private val syncStudentsUseCase: SyncStudentsUseCase,
    private val rebaseStudentsUseCase: RebaseStudentsUseCase,
    private val syncProjectsUseCase: SyncProjectsUseCase,
    private val rebaseProjectsUseCase: RebaseProjectsUseCase,
    private val syncParticipationUseCase: SyncParticipationUseCase,
    private val rebaseParticipationUseCase: RebaseParticipationUseCase,
    private val uploadSupervisorsUseCase: UploadSupervisorsUseCase,
    private val uploadDepartmentsUseCase: UploadDepartmentsUseCase,
    private val uploadInstitutesUseCase: UploadInstitutesUseCase,
    private val cancelOperationsUseCase: CancelOperationsUseCase,
    downloadProgressInteractor: DownloadProgressInteractor,
) : BaseViewModel() {

    val studentsDownloadFlow = downloadProgressInteractor.studentsDownloadFlow
    val projectsDownloadFlow = downloadProgressInteractor.projectsDownloadFlow
    val participationsDownloadFlow = downloadProgressInteractor.participationsDownloadFlow
    val institutesDownloadFlow = downloadProgressInteractor.institutesDownloadFlow
    val departmentsDownloadFlow = downloadProgressInteractor.departmentsDownloadFlow
    val supervisorsDownloadFlow = downloadProgressInteractor.supervisorsDownloadFlow

    fun syncData() {
        coroutineScope.launch {
            syncDataUseCase()
        }
    }

    fun rebaseData() {
        coroutineScope.launch {
            rebaseDataUseCase()
        }
    }

    fun syncByDownloadType(downloadType: DownloadType) {
        coroutineScope.launch {
            when (downloadType) {
                DownloadType.STUDENTS -> {
                    syncStudentsUseCase()
                }
                DownloadType.PROJECTS -> {
                    syncProjectsUseCase()
                }
                DownloadType.PARTICIPATIONS -> {
                    syncParticipationUseCase()
                }
                else -> {}
            }
        }
    }

    fun rebaseByDownloadType(downloadType: DownloadType) {
        coroutineScope.launch {
            when (downloadType) {
                DownloadType.STUDENTS -> {
                    rebaseStudentsUseCase()
                }
                DownloadType.PROJECTS -> {
                    rebaseProjectsUseCase()
                }
                DownloadType.PARTICIPATIONS -> {
                    rebaseParticipationUseCase()
                }
                DownloadType.DEPARTMENTS -> {
                    uploadDepartmentsUseCase()
                }
                DownloadType.SUPERVISORS -> {
                    uploadSupervisorsUseCase()
                }
                DownloadType.INSTITUTES -> {
                    uploadInstitutesUseCase()
                }
            }
        }
    }

    fun cancelOperations() {
        coroutineScope.launch {
            cancelOperationsUseCase()
        }
    }
}

enum class DataActionType {
    SYNC,
    REBASE
}

enum class DownloadType(val title: String, val isMutable: Boolean, val order: Int): Comparator<DownloadType> {
    STUDENTS("Студенты", true, 0),
    PROJECTS("Проекты", true, 1),
    PARTICIPATIONS("Заявки", true, 2),
    INSTITUTES("Институты", false, 3),
    DEPARTMENTS("Кафедры", false, 4),
    SUPERVISORS("Преподаватели", false, 5);

    override fun compare(p0: DownloadType?, p1: DownloadType?): Int {
        return if (p0!!.order < p1!!.order) -1
        else if (p0.order > p1.order) 1
        else 0
    }
}