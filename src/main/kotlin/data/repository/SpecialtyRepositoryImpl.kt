package data.repository

import data.local.dao.SpecialtyDao
import data.mapper.specialtyResponseToSpecialty
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Specialty
import domain.repository.SpecialtyRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpecialtyRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val specialtyDao: SpecialtyDao,
    private val projectFairApi: OrdinaryProjectFairApi,
) : SpecialtyRepository {

    override val downloadFlow = MutableStateFlow<Float>(0f)

    override fun getSpecialties(): Flow<ResultsChange<Specialty>> {
        return specialtyDao.getAll()
    }

    override suspend fun updateSpecialty(specialties: Specialty) {
        specialtyDao.update(specialties)
    }

    override suspend fun insertSpecialty(specialty: Specialty) {
        withContext(ioDispatcher) {
            specialtyDao.insert(specialty)
        }
    }

    override suspend fun insertSpecialty(specialties: List<Specialty>) {
        withContext(ioDispatcher) {
            specialtyDao.insert(specialties)
        }
    }

    override suspend fun deleteSpecialty(specialty: Specialty) {
        specialtyDao.delete<Specialty>(specialty)
    }

    override suspend fun deleteAllSpecialties() {
        specialtyDao.deleteAll<Specialty>()
    }

    override suspend fun uploadSpecialties() {
        withContext(ioDispatcher) {
            val specialties = projectFairApi.getSpecialties()
            var current = 0f
            val overall = specialties.size

            specialties.forEach {
                insertSpecialty(specialtyResponseToSpecialty(it)!!)
                downloadFlow.value = ++current / overall
            }
        }
    }
}