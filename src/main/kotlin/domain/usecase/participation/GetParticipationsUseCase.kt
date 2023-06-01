package domain.usecase.participation

import domain.model.Participation
import domain.repository.ParticipationRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetParticipationsUseCase(
    private val participationRepository: ParticipationRepository
) {

    operator fun invoke(): Flow<ResultsChange<Participation>> = participationRepository.getParticipations()
}