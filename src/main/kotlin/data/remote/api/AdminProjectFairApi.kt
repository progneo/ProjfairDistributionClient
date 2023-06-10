package data.remote.api

import data.dto.DepartmentResponse
import data.dto.ParticipationResponse
import data.dto.ProjectResponse
import domain.model.Participation
import retrofit2.http.*

interface AdminProjectFairApi {

    @PATCH("participations/{id}")
    suspend fun updateParticipation(
        @Path("id") id: Int,
        @Body participation: ParticipationResponse
    )

    @POST("participations")
    suspend fun createParticipation(
        @Body participation: ParticipationResponse
    )

    @PATCH("projects/{id}")
    suspend fun updateProject(
        @Path("id") id: Int,
        @Body project: ProjectResponse
    )
}