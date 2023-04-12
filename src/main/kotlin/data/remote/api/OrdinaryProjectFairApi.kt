package data.remote.api

import data.dto.*
import retrofit2.http.GET

interface OrdinaryProjectFairApi {
    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>

    @GET("candidates")
    suspend fun getCandidates(): List<StudentResponse>

    @GET("projects/filter?state=[2]&pageSize=max")
    suspend fun getProjects(): ProjectsResponse

    @GET("participations/filter?state=[2]&pageSize=max")
    suspend fun getParticipations(): ParticipationsResponse
}