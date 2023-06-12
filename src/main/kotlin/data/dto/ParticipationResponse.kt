package data.dto

import com.google.gson.annotations.SerializedName

data class ParticipationStateObjectResponse(
    val id: Int,
    val priority: Int,
    @SerializedName("project_id") val projectId: String,
    @SerializedName("candidate_id") val candidateId: String,
    val candidate: StudentParticipationResponse? = null,
    @SerializedName("updated_at") val updatedAt: String,
    val state: ParticipationStateResponse
)

data class ParticipationStateIntResponse(
    val id: Int,
    val priority: Int,
    @SerializedName("project_id") val projectId: String,
    @SerializedName("candidate_id") val candidateId: String,
    val candidate: StudentParticipationResponse? = null,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("state_id") val state: Int
)