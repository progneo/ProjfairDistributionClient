package data.dto

data class ParticipationsResponse(
    val data: List<ParticipationStateObjectResponse>,
    val participationCount: Int
)