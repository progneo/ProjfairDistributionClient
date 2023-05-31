package data.repository

import data.local.dao.InstituteDao
import data.mapper.instituteResponseToInstitute
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Institute
import domain.repository.InstituteRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InstituteRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val instituteDao: InstituteDao,
    private val projectFairApi: OrdinaryProjectFairApi,
) : InstituteRepository {

    override val downloadFlow = MutableStateFlow<Float>(0f)

    override fun getInstitutes(): Flow<ResultsChange<Institute>> {
        return instituteDao.getAll()
    }

    override suspend fun updateInstitute(institutes: Institute) {
        //instituteDao.update(institutes)
    }

    override suspend fun insertInstitute(institute: Institute) {
        withContext(ioDispatcher) {
            instituteDao.insert(institute)
        }
    }

    override suspend fun insertInstitute(institutes: List<Institute>) {
        withContext(ioDispatcher) {
            instituteDao.insert(institutes)
        }
    }

    override suspend fun deleteInstitute(institute: Institute) {
        instituteDao.delete<Institute>(institute)
    }

    override suspend fun deleteAllInstitutes() {
        instituteDao.deleteAll<Institute>()
    }

    override suspend fun uploadInstitutes() {
        withContext(ioDispatcher) {
            val institutes = projectFairApi.getInstitutes().map { instituteResponseToInstitute(it) }
            insertInstitute(institutes)
            downloadFlow.value = 1f
        }
    }
}