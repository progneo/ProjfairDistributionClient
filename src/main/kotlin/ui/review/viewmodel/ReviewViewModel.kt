package ui.review.viewmodel

import androidx.compose.runtime.mutableStateOf
import domain.model.Participation
import domain.model.Project
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.logging.GetLogsUseCase
import domain.usecase.logging.SaveLogUseCase
import domain.usecase.participation.*
import domain.usecase.project.*
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import kotlinx.coroutines.*
import ui.common.BaseGodViewModel
import ui.common.BaseGodViewModelType
import ui.preview.widget.PreviewTabPage

class ReviewViewModel(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
    private val syncProjectUseCase: SyncProjectUseCase,
    private val getLogsUseCase: GetLogsUseCase,
    private val saveLogUseCase: SaveLogUseCase,
    private val insertParticipationOnServerUseCase: InsertParticipationOnServerUseCase,
    private val updateProjectsOnServerUseCase: UpdateProjectsOnServerUseCase,
    private val updateParticipationOnServerUseCase: UpdateParticipationOnServerUseCase,
    private val getParticipationLastIndexUseCase: GetParticipationLastIndexUseCase
) : BaseGodViewModel(
    getStudentsUseCase = getStudentsUseCase,
    getProjectsUseCase = getProjectsUseCase,
    updateProjectUseCase = updateProjectUseCase,
    getParticipationsUseCase = getParticipationsUseCase,
    getInstitutesUseCase = getInstitutesUseCase,
    getDepartmentsUseCase = getDepartmentsUseCase,
    getSpecialtiesUseCase = getSpecialtiesUseCase,
    getSupervisorsUseCase = getSupervisorsUseCase,
    syncProjectUseCase = syncProjectUseCase,
    getLogsUseCase = getLogsUseCase,
    saveLogUseCase = saveLogUseCase
) {

    var reviewTabPage = mutableStateOf(PreviewTabPage.Students)

    private val newCoroutineScope = CoroutineScope(Dispatchers.IO)

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println(exception)
    }

    override fun getType(): BaseGodViewModelType {
        return BaseGodViewModelType.REVIEW
    }

    fun updateOnServer(
        insertParticipation: List<Participation> = emptyList(),
        updateProjects: List<Project> = emptyList(),
        updateParticipation: List<Participation> = emptyList(),
    ) {
        newCoroutineScope.launch(exceptionHandler) {
            //insertParticipationOnServerUseCase(insertParticipation)
            updateProjectsOnServerUseCase(updateProjects)
            updateParticipationOnServerUseCase(updateParticipation+insertParticipation)
        }
    }

    fun getParticipationLastIndex(): Int {
        return getParticipationLastIndexUseCase()
    }

    fun stopLoading() {
        newCoroutineScope.coroutineContext.cancelChildren()
    }
}