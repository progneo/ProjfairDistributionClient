package ui.preview.viewmodel

import androidx.compose.runtime.mutableStateOf
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import ui.common.BaseGodViewModel
import ui.preview.widget.PreviewTabPage

class PreviewViewModel constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
) : BaseGodViewModel(
    getStudentsUseCase = getStudentsUseCase,
    getProjectsUseCase = getProjectsUseCase,
    updateProjectUseCase = updateProjectUseCase,
    getParticipationsUseCase = getParticipationsUseCase,
    getInstitutesUseCase = getInstitutesUseCase,
    getDepartmentsUseCase = getDepartmentsUseCase,
    getSpecialtiesUseCase = getSpecialtiesUseCase,
    getSupervisorsUseCase = getSupervisorsUseCase
) {

    var previewTabPage = mutableStateOf(PreviewTabPage.Students)
}