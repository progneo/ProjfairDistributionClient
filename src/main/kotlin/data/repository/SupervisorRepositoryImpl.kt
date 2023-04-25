package data.repository

import data.local.dao.SupervisorDao
import data.mapper.supervisorDetailsResponseToSupervisor
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Supervisor
import domain.repository.SupervisorRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SupervisorRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val supervisorDao: SupervisorDao,
    private val projectFairApi: OrdinaryProjectFairApi,
) : SupervisorRepository {

    override val downloadFlow = MutableStateFlow(0f)

    override fun getSupervisors(): Flow<ResultsChange<Supervisor>> {
        return supervisorDao.getAll()
    }

    override suspend fun updateSupervisor(supervisor: Supervisor) {
        supervisorDao.update(supervisor)
    }

    override suspend fun insertSupervisor(supervisor: Supervisor) {
        withContext(ioDispatcher) {
            supervisorDao.insert(supervisor)
        }
    }

    override suspend fun insertSupervisor(supervisors: List<Supervisor>) {
        withContext(ioDispatcher) {
            supervisorDao.insert(supervisors)
        }
    }

    override suspend fun deleteSupervisor(supervisor: Supervisor) {
        supervisorDao.delete<Supervisor>(supervisor)
    }

    override suspend fun deleteAllSupervisors() {
        supervisorDao.deleteAll<Supervisor>()
    }

    override suspend fun uploadSupervisors() {
        withContext(ioDispatcher) {
            val supervisors = projectFairApi.getSupervisors()
            val oldSupervisors = supervisorDao.getAll<Supervisor>().first().list
            val oldMap = mutableMapOf<Int, Supervisor>()
            oldSupervisors.forEach {
                oldMap[it.id] = it
            }
            var current = 0f
            val overall = supervisors.size

            supervisors.forEach { supervisorDetailsResponse ->
                val newSupervisor = supervisorDetailsResponseToSupervisor(supervisorDetailsResponse)
                val oldSupervisor = oldMap[newSupervisor.id]
                if (oldSupervisor == null || oldSupervisor != newSupervisor) {
                    insertSupervisor(newSupervisor)
                }
                downloadFlow.value = ++current / overall
            }
        }
    }
}