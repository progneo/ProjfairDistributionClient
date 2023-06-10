package data.mapper

import data.dto.ParticipationResponse
import domain.model.Participation
import ui.details.participation.viewmodel.ParticipationDetailsViewModel.Companion.projectId

fun participationToParticipationResponse(participation: Participation): ParticipationResponse {
    return ParticipationResponse(
        id = participation.id,
        projectId = participation.projectId.toString(),
        priority = participation.priority,
        candidateId = participation.studentId.toString(),
        updatedAt = participation.updatedAt
    )
}