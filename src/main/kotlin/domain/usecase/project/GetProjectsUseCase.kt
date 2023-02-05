package domain.usecase.project

import domain.model.Project
import domain.repository.ProjectRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {

    operator fun invoke(): Flow<ResultsChange<Project>> = projectRepository.getProjects()
}