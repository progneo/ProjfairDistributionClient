package domain.usecase.project

import domain.model.Project
import domain.repository.ProjectRepository
import javax.inject.Inject

class UpdateProjectUseCase(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(project: Project) {
        projectRepository.updateProject(project)
    }
}