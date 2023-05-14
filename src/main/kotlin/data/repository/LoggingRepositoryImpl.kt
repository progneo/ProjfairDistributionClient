package data.repository

import data.local.dao.LogDao
import domain.model.Department
import domain.model.Log
import domain.model.LogSource
import domain.model.LogType
import domain.repository.LoggingRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class LoggingRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val logDao: LogDao
): LoggingRepository {

    override fun getLogs(): Flow<ResultsChange<Log>> {
        return logDao.getAll()
    }

    override suspend fun saveLog(log: Log, logType: LogType, logSource: LogSource) {
        withContext(ioDispatcher) {
            logDao.insert(log, logType, logSource)
        }
    }

    override suspend fun deleteAll() {
        logDao.deleteAll()
    }
}