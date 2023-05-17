package ui.details.participation.viewmodel

import base.mvi.BaseViewModel
import domain.interactor.DownloadProgressInteractor
import domain.model.Participation
import domain.model.Project
import domain.model.Student
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.uploaddata.RebaseDataUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.uploaddata.contract.UploadDataContract
import javax.inject.Inject

class ParticipationDetailsViewModel @Inject constructor(
    private val getParticipationsUseCase: GetParticipationsUseCase,
) : BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    companion object {
        var projectId: Int? = null
    }

    override fun createInitialState(): UploadDataContract.ScreenState {
        return UploadDataContract.ScreenState.Idle
    }

    override fun handleIntent(intent: UploadDataContract.Intent) {}

    var selectedProjectStudents = MutableStateFlow<MutableList<Student>>(mutableListOf())
    var selectedChooseStudents = MutableStateFlow<MutableList<Student>>(mutableListOf())
    var projectParticipation = MutableStateFlow<MutableList<Participation>>(mutableListOf())
    var requiredParticipation = MutableStateFlow<MutableList<Participation>>(mutableListOf())
    var currentProject: Project? = null

    init {
        setParticipation()
    }

    private fun setParticipation() {
        coroutineScope.launch {
            getParticipationsUseCase().collect {
                projectParticipation.value = it.list.filter { part ->
                    part.projectId == projectId
                }.sortedBy { part ->
                    part.priority
                }.toMutableList()
                requiredParticipation.value = it.list.toMutableList()
            }
        }
    }

    fun setProjectStudents(participation: List<Participation>) {
        projectParticipation.value = participation.toMutableList()
    }

    fun setRequiredParticipation(participation: List<Participation>) {
        requiredParticipation.value = participation.toMutableList()
    }

    fun clearSelectedProjectStudents() {
        selectedProjectStudents.value = mutableListOf()
    }

    fun clearSelectedChooseStudents() {
        selectedChooseStudents.value = mutableListOf()
    }
}