package domain.repository

import domain.model.Project
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<ResultsChange<Project>>
    suspend fun uploadProjects()
    suspend fun updateProject(project: Project)
    suspend fun insertProject(project: Project)
    suspend fun insertProject(projects: List<Project>)
    suspend fun deleteProject(project: Project)
    suspend fun deleteAllProjects()
}