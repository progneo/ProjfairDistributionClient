package domain.repository

import domain.model.Department
import domain.model.Log
import domain.model.LogSource
import domain.model.LogType
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface LoggingRepository {
    fun getLogs(): Flow<ResultsChange<Log>>
    suspend fun saveLog(log: Log, logType: LogType, logSource: LogSource)
    suspend fun deleteAll()
}