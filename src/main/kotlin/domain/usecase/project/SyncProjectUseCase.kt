package domain.usecase.project

import domain.model.Project
import domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncProjectUseCase(
    private val projectRepository: ProjectRepository,
) {

    suspend operator fun invoke(id: Int): Flow<Project> = flow {
        emit(projectRepository.syncProjectById(id))
    }
}