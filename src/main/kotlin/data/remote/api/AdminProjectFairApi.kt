package data.remote.api

import data.dto.*
import domain.model.Participation
import retrofit2.http.*

interface AdminProjectFairApi {

    @PATCH("participations/{id}")
    suspend fun updateParticipation(
        @Path("id") id: Int,
        @Body participation: ParticipationStateIntResponse
    )

    @POST("participations")
    suspend fun createParticipation(
        @Body participation: ParticipationStateIntResponse
    )

    @POST("participations/updateOrCreate")
    suspend fun createOrUpdateParticipation(
        @Body participation: ParticipationStateIntResponse
    )

    @PATCH("projects/{id}")
    suspend fun updateProject(
        @Path("id") id: Int,
        @Body project: ProjectResponse
    )
}