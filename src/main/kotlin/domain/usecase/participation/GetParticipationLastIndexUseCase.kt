package domain.usecase.participation

import di.Review
import domain.repository.ParticipationRepository
import javax.inject.Inject

class GetParticipationLastIndexUseCase @Inject constructor(
    @Review private val participationRepository: ParticipationRepository
) {

    operator fun invoke(): Int {
        return participationRepository.getParticipationLastIndex()
    }
}

