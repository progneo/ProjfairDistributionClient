package data.mapper

import data.dto.ParticipationResponse
import domain.model.Participation

fun participationResponseToParticipation(participation: ParticipationResponse): Participation {
    return Participation(
        id = participation.id,
        studentId = participation.candidateId.toInt(),
        studentNumz = participation.candidate.numz.toInt(),
        projectId = participation.projectId.toInt(),
        priority = participation.priority
    )
}