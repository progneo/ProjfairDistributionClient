package domain.repository

import domain.model.Institute
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface InstituteRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getInstitutes(): Flow<ResultsChange<Institute>>
    suspend fun updateInstitute(institutes: Institute)
    suspend fun insertInstitute(institute: Institute)
    suspend fun insertInstitute(institutes: List<Institute>)
    suspend fun deleteInstitute(institute: Institute)
    suspend fun deleteAllInstitutes()
    suspend fun uploadInstitutes()
}