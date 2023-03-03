package data.remote.api

import data.dto.ParticipationResponse
import retrofit2.http.GET

interface AdminProjectFairApi {

    @GET("participations")
    suspend fun getParticipations(): List<ParticipationResponse>
}