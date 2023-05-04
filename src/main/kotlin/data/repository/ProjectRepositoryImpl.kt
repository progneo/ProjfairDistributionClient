package data.repository

import data.local.dao.ProjectDao
import data.mapper.projectResponseToProject
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Project
import domain.model.Student
import domain.repository.ProjectRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val projectDao: ProjectDao,
    private val projectFairApi: OrdinaryProjectFairApi
): ProjectRepository {

    override val downloadFlow = MutableStateFlow<Float>(0f)

    override fun getProjects(): Flow<ResultsChange<Project>> {
        return projectDao.getAll()
    }

    override suspend fun updateProject(project: Project) {
        projectDao.update(project)
    }

    override suspend fun insertProject(project: Project) {
        withContext(ioDispatcher) {
            projectDao.insert(project)
        }
    }

    override suspend fun insertProject(projects: List<Project>) {
        withContext(ioDispatcher) {
            projectDao.insert(projects)
        }
    }

    override suspend fun deleteProject(project: Project) {
        projectDao.delete<Student>(project)
    }

    override suspend fun deleteAllProjects() {
        projectDao.deleteAll<Project>()
    }

    override suspend fun syncProjects() {
        withContext(ioDispatcher) {
            val projects = projectFairApi.getProjects().data
            val oldProjects = projectDao.getAll<Project>().first().list
            val oldMap = mutableMapOf<Int, Project>()
            oldProjects.forEach {
                oldMap[it.id] = it
            }
            var current = 0f
            val overall = projects.size

            projects.forEach {
                val newProject = projectResponseToProject(it)
                val oldProject = oldMap[newProject.id]
                if (oldProject == null) {
                    insertProject(newProject)
                }
                downloadFlow.value = ++current / overall
            }
        }
    }

    override suspend fun rebaseProjects() {
        withContext(ioDispatcher) {
            val projects = projectFairApi.getProjects().data
            var current = 0f
            val overall = projects.size

            projects.forEach {
                val newProject = projectResponseToProject(it)
                insertProject(newProject)
                downloadFlow.value = ++current / overall
            }
        }
    }
}