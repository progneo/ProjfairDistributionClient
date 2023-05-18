package domain.usecase.participation

import domain.repository.ParticipationRepository
import javax.inject.Inject

class SyncParticipationUseCase @Inject constructor(
    private val participationRepository: ParticipationRepository
) {

    suspend operator fun invoke() {
        participationRepository.syncParticipations()
    }
}