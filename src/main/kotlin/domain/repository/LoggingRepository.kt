package domain.repository

import domain.model.Department
import domain.model.Log
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface LoggingRepository {
    fun getLogs(): Flow<ResultsChange<Log>>
    suspend fun saveLog(log: Log)
}