package data.mapper

import data.dto.ParticipationStateObjectResponse
import domain.model.Participation

fun participationResponseToParticipation(participation: ParticipationStateObjectResponse): Participation {
    return Participation(
        id = participation.id,
        studentId = participation.candidateId.toInt(),
        studentNumz = participation.candidate?.numz!!.toInt(),
        studentName = participation.candidate.fio,
        projectId = participation.projectId.toInt(),
        priority = participation.priority,
        updatedAt = participation.updatedAt,
        state = participation.state.id
    )
}