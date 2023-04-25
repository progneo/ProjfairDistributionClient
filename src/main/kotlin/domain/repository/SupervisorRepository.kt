package domain.repository

import domain.model.Supervisor
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SupervisorRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getSupervisors(): Flow<ResultsChange<Supervisor>>
    suspend fun updateSupervisor(supervisor: Supervisor)
    suspend fun insertSupervisor(supervisor: Supervisor)
    suspend fun insertSupervisor(supervisors: List<Supervisor>)
    suspend fun deleteSupervisor(supervisor: Supervisor)
    suspend fun deleteAllSupervisors()
    suspend fun uploadSupervisors()
}