package domain.repository

import domain.model.Participation
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ParticipationRepository {
    val downloadFlow: MutableStateFlow<Float>
    fun getParticipations(): Flow<ResultsChange<Participation>>
    fun getParticipationLastIndex(): Int
    suspend fun updateParticipation(participation: Participation)
    suspend fun updateParticipation(participation: List<Participation>)
    suspend fun updateParticipationOnServer(participation: List<Participation>)
    suspend fun insertParticipation(participation: Participation, byRebase: Boolean)
    suspend fun insertParticipation(participations: List<Participation>)
    suspend fun insertParticipationOnServer(participations: List<Participation>)
    suspend fun deleteParticipation(participation: Participation, byServer: Boolean)
    suspend fun deleteParticipation(participation: List<Participation>, byServer: Boolean)
    suspend fun deleteAllParticipations()
    suspend fun syncParticipations()
    suspend fun rebaseParticipations()
}