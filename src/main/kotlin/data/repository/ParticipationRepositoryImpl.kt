package data.repository

import data.local.dao.ParticipationDao
import data.mapper.participationResponseToParticipation
import data.remote.api.AdminProjectFairApi
import domain.model.Participation
import domain.repository.ParticipationRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ParticipationRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val participationDao: ParticipationDao,
    private val adminProjectFairApi: AdminProjectFairApi,
) : ParticipationRepository {

    override fun getParticipations(): Flow<ResultsChange<Participation>> {
        return participationDao.getAll()
    }

    override suspend fun updateParticipation(participation: Participation) {
        participationDao.update(participation)
    }

    override suspend fun insertParticipation(participation: Participation) {
        withContext(ioDispatcher) {
            participationDao.insert(participation)
        }
    }

    override suspend fun insertParticipation(participations: List<Participation>) {
        withContext(ioDispatcher) {
            participationDao.insert(participations)
        }
    }

    override suspend fun deleteParticipation(participation: Participation) {
        participationDao.delete<Participation>(participation)
    }

    override suspend fun deleteAllParticipations() {
        participationDao.deleteAll<Participation>()
    }

    override suspend fun uploadParticipations() {
        withContext(ioDispatcher) {
            val participations = adminProjectFairApi.getParticipations()

            participations.forEach {
                insertParticipation(participationResponseToParticipation(it))
            }
        }
    }
}