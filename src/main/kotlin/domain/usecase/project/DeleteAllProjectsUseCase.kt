package domain.usecase.project

import di.Review
import domain.model.Project
import domain.repository.ProjectRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllProjectsUseCase @Inject constructor(
    @Review private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke() {
        projectRepository.deleteAllProjects()
    }
}
