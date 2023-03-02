package data.dto

import com.google.gson.annotations.SerializedName

data class ParticipationResponse(
    val id: Int,
    val priority: Int,
    @SerializedName("project_id") val projectId: String,
    @SerializedName("state_id") val stateId: String,
    @SerializedName("candidate_id") val candidateId: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)