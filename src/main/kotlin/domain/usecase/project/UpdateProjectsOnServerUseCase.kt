package domain.usecase.project

import di.Review
import domain.model.Project
import domain.repository.ProjectRepository
import javax.inject.Inject

class UpdateProjectsOnServerUseCase @Inject constructor(
    @Review private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(projects: List<Project>) {
        projectRepository.updateProjectOnServer(projects)
    }
}
