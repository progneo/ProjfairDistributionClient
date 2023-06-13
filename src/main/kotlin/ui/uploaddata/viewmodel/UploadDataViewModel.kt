package ui.uploaddata.viewmodel

import base.mvi.BaseViewModel
import di.Preview
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import ui.uploaddata.contract.UploadDataContract
import java.lang.IllegalStateException
import javax.inject.Inject

class UploadDataViewModel(
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

    val error = MutableStateFlow("")
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        println(exception)
        error.value = exception.toString()
    }

    private var newCoroutineScope = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)

    private fun updateCoroutineScope() {
        if (!newCoroutineScope.isActive) {
            newCoroutineScope = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)
        }
    }

    fun syncData() {
        updateCoroutineScope()
        newCoroutineScope.launch {
            syncDataUseCase()
        }
    }

    fun rebaseData() {
        updateCoroutineScope()
        newCoroutineScope.launch {
            rebaseDataUseCase()
        }
    }

    fun syncByDownloadType(downloadType: DownloadType) {
        updateCoroutineScope()
        newCoroutineScope.launch {
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
        updateCoroutineScope()
        newCoroutineScope.launch {
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
        cancelOperationsUseCase()
        newCoroutineScope.coroutineContext.cancelChildren()
    }
}

enum class DownloadType(val title: String, val isMutable: Boolean, val order: Int) : Comparator<DownloadType> {
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