package data.repository

import common.date.getCurrentDateTime
import data.local.dao.ParticipationDao
import data.mapper.participationResponseToParticipation
import data.remote.api.OrdinaryProjectFairApi
import domain.model.*
import domain.repository.LoggingRepository
import domain.repository.ParticipationRepository
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.RealmAny
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class ParticipationRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val participationDao: ParticipationDao,
    private val ordinaryProjectFairApi: OrdinaryProjectFairApi,
    private val loggingRepository: LoggingRepository
) : ParticipationRepository {

    override val downloadFlow = MutableStateFlow<Float>(0f)

    override fun getParticipations(): Flow<ResultsChange<Participation>> {
        return participationDao.getAll()
    }

    override suspend fun updateParticipation(participation: Participation) {
        //participationDao.update(participation)
    }

    override suspend fun insertParticipation(participation: Participation, byRebase: Boolean) {
        withContext(ioDispatcher) {
            participationDao.insert(participation)
            if (!byRebase) {
                loggingRepository.saveLog(
                    log = Log(
                        id = UUID.randomUUID().toString(),
                        dateTime = getCurrentDateTime(),
                        participation = participation,
                    ),
                    logType = LogType.SAVE,
                    logSource = LogSource.SERVER
                )
            }
        }
    }

    override suspend fun insertParticipation(participations: List<Participation>) {
        withContext(ioDispatcher) {
            participationDao.insert(participations)
        }
    }

    override suspend fun deleteParticipation(participation: Participation, byServer: Boolean) {
        participationDao.delete<Participation>(participation)
        loggingRepository.saveLog(
            log = Log(
                id = UUID.randomUUID().toString(),
                dateTime = getCurrentDateTime(),
                participation = participation,
            ),
            logType = LogType.REMOVE,
            logSource = if (byServer) LogSource.SERVER else LogSource.USER
        )
    }

    override suspend fun deleteAllParticipations() {
        participationDao.deleteAll<Participation>()
    }

    override suspend fun syncParticipations() {

        data class ParticipationAlive(
            var isAlive: Boolean,
            val participation: Participation,
        )

        withContext(ioDispatcher) {
            val participations = ordinaryProjectFairApi.getParticipations().data
            val oldParticipations = participationDao.getAll<Participation>().first().list
            val oldMap = mutableMapOf<Int, ParticipationAlive>()
            oldParticipations.forEach {
                oldMap[it.id] = ParticipationAlive(false, it)
            }
            var current = 0f
            val overall = participations.size

            participations.forEach {
                val newParticipation = participationResponseToParticipation(it)
                val oldParticipation = oldMap[newParticipation.id]
                if (oldParticipation == null) {
                    insertParticipation(newParticipation, false)
                } else {
                    oldMap[newParticipation.id]!!.isAlive = true
                }
                downloadFlow.value = ++current / overall
            }

            oldMap.filter { !it.value.isAlive }.forEach {
                deleteParticipation(it.value.participation, true)
            }
        }
    }

    override suspend fun rebaseParticipations() {
        withContext(ioDispatcher) {
            val participations = ordinaryProjectFairApi.getParticipations().data
            deleteAllParticipations()
            val newParticipations = participations.map { participationResponseToParticipation(it) }
            insertParticipation(newParticipations)
            downloadFlow.value = 1f
        }
    }
}