package domain.usecase.project

import domain.repository.ParticipationRepository
import domain.repository.ProjectRepository
import javax.inject.Inject

class SyncProjectsUseCase(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke() {
        projectRepository.syncProjects()
    }
}