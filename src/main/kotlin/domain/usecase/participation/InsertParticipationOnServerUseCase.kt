package domain.usecase.participation

import di.Review
import domain.model.Participation
import domain.repository.ParticipationRepository
import javax.inject.Inject

class InsertParticipationOnServerUseCase @Inject constructor(
    @Review private val participationRepository: ParticipationRepository
) {

    suspend operator fun invoke(participation: List<Participation>) {
        participationRepository.insertParticipationOnServer(participation)
    }
}

