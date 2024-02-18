package domain.usecase.participation

import domain.model.Participation
import domain.repository.ParticipationRepository

class DeleteParticipationUseCase(
    private val participationRepository: ParticipationRepository,
) {
    suspend operator fun invoke(participation: List<Participation>) {
        participationRepository.deleteParticipation(participation, false)
    }
}
