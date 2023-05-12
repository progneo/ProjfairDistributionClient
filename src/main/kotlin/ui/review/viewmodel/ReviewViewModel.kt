package ui.review.viewmodel

import androidx.compose.runtime.mutableStateOf
import domain.model.GeneratedDistribution
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.file.GetGeneratedDistributionUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.SyncProjectUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import ru.student.distribution.model.DistributionResults
import ui.common.BaseGodViewModel
import ui.preview.widget.PreviewTabPage
import javax.inject.Inject

class ReviewViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val getParticipationsUseCase: GetParticipationsUseCase,
    private val getInstitutesUseCase: GetInstitutesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getSupervisorsUseCase: GetSupervisorsUseCase,
    private val getGeneratedDistributionUseCase: GetGeneratedDistributionUseCase,
    private val syncProjectUseCase: SyncProjectUseCase,
) : BaseGodViewModel(
    getStudentsUseCase = getStudentsUseCase,
    getProjectsUseCase = getProjectsUseCase,
    updateProjectUseCase = updateProjectUseCase,
    getParticipationsUseCase = getParticipationsUseCase,
    getInstitutesUseCase = getInstitutesUseCase,
    getDepartmentsUseCase = getDepartmentsUseCase,
    getSpecialtiesUseCase = getSpecialtiesUseCase,
    getSupervisorsUseCase = getSupervisorsUseCase,
    syncProjectUseCase = syncProjectUseCase
) {

    var reviewTabPage = mutableStateOf(PreviewTabPage.Students)

    fun getGeneratedDistribution(): GeneratedDistribution {
        //return getGeneratedDistributionUseCase()
        return GeneratedDistribution(
            id = 0,
            results = DistributionResults(
                allParticipation = emptyList(),
                institutesResults = emptyList()
            )
        )
    }
}