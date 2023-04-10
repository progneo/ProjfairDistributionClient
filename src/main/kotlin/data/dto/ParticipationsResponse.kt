package data.dto

data class ParticipationsResponse(
    val data: List<ParticipationResponse>,
    val participationCount: Int
)