package domain.repository

import domain.model.Specialty
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SpecialtyRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getSpecialties(): Flow<ResultsChange<Specialty>>
    suspend fun updateSpecialty(specialties: Specialty)
    suspend fun insertSpecialty(specialty: Specialty)
    suspend fun insertSpecialty(specialties: List<Specialty>)
    suspend fun deleteSpecialty(specialty: Specialty)
    suspend fun deleteAllSpecialties()
    suspend fun uploadSpecialties()
}