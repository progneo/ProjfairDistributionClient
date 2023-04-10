package data.remote.api

import data.dto.*
import retrofit2.http.GET

interface OrdinaryProjectFairApi {
    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>

    @GET("candidates")
    suspend fun getCandidates(): List<StudentResponse>

//    @GET("projects/filter")
//    suspend fun getProjects(
//        @Query ("pageSize") pageSize: String = "max"
//    ): List<ProjectResponse>

    @GET("projects/filter?pageSize=max&state=[2]")
    suspend fun getProjects(
        //@Query ("pageSize") pageSize: String = "max"
    ): ProjectsResponse

    @GET("participations/filter?state=[2]&pageSize=max")
    suspend fun getParticipations(): ParticipationsResponse
}