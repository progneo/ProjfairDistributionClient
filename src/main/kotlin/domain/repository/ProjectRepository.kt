package domain.repository

import domain.model.Project
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ProjectRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getProjects(): Flow<ResultsChange<Project>>
    suspend fun updateProject(project: Project)
    suspend fun insertProject(project: Project)
    suspend fun insertProject(projects: List<Project>)
    suspend fun deleteProject(project: Project)
    suspend fun deleteAllProjects()
    suspend fun syncProjects()
    suspend fun rebaseProjects()
}