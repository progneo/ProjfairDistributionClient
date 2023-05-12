package data.remote.api

import data.dto.*
import retrofit2.http.GET
import retrofit2.http.Path

interface OrdinaryProjectFairApi {
    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>

    @GET("candidates")
    suspend fun getCandidates(): List<StudentResponse>

    @GET("projects/filter?state=[2]&pageSize=max")
    suspend fun getProjects(): ProjectsResponse

    @GET("projects/{id}")
    suspend fun getProjectById(@Path("id") id: Int): ProjectResponse

    @GET("participations/filter?state=[2]&pageSize=max")
    suspend fun getParticipations(): ParticipationsResponse

    @GET("specialities")
    suspend fun getSpecialties(): List<SpecialtyResponse>

    @GET("supervisors")
    suspend fun getSupervisors(): List<SupervisorDetailsResponse>
}