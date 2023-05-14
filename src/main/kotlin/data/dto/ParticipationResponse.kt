package data.dto

import com.google.gson.annotations.SerializedName

data class ParticipationResponse(
    val id: Int,
    val priority: Int,
    @SerializedName("project_id") val projectId: String,
    @SerializedName("candidate_id") val candidateId: String,
    val numzResponse: StudentNumzResponse
)