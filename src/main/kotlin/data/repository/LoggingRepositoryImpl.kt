package data.repository

import data.local.dao.LogDao
import domain.model.Department
import domain.model.Log
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

    override suspend fun saveLog(log: Log) {
        withContext(ioDispatcher) {
            logDao.insert(log)
        }
    }
}