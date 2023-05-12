package domain.usecase.project

import domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
) {

    suspend operator fun invoke(id: Int): Flow<Boolean> = flow {
        emit(projectRepository.syncProjectById(id))
    }
}