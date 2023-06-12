package data.mapper

import data.dto.ParticipationStateIntResponse
import data.dto.ParticipationStateObjectResponse
import domain.model.Participation
import ui.details.participation.viewmodel.ParticipationDetailsViewModel.Companion.projectId

fun participationToParticipationResponse(participation: Participation): ParticipationStateIntResponse {
    return ParticipationStateIntResponse(
        id = participation.id,
        projectId = participation.projectId.toString(),
        priority = participation.priority,
        candidateId = participation.studentId.toString(),
        updatedAt = participation.updatedAt,
        state = participation.state
    )
}