package ui.preview.viewmodel

import androidx.compose.runtime.mutableStateOf
import di.Preview
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.logging.GetLogsUseCase
import domain.usecase.logging.SaveLogUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.SyncProjectUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import ui.common.BaseGodViewModel
import ui.common.BaseGodViewModelType
import ui.preview.widget.PreviewTabPage
import javax.inject.Inject

class PreviewViewModel(
    @Preview private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
    private val syncProjectUseCase: SyncProjectUseCase,
    private val getLogsUseCase: GetLogsUseCase,
    private val saveLogUseCase: SaveLogUseCase
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
    var previewTabPage = mutableStateOf(PreviewTabPage.Students)

    init {
        println("INIT PREVIEW")
    }

    override fun getType(): BaseGodViewModelType {
        return BaseGodViewModelType.PREVIEW
    }
}