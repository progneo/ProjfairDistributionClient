package data.remote.api

import data.dto.*
import data.enums.ParticipationState
import data.enums.ProjectState
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdinaryProjectFairApi {
    @GET("departments")
    suspend fun getDepartments(): List<DepartmentResponse>

    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>

    @GET("candidates")
    suspend fun getCandidates(): List<StudentResponse>

    @GET("projects/filter")
    suspend fun getProjects(
        @Query("state") states: String =
            arrayListOf(ProjectState.COLLECT_PARTICIPATION.id)
                .joinToString(",", "[", "]"),
        @Query("pageSize") pageSize: String = "max",
    ): ProjectsResponse

    @GET("projects/{id}")
    suspend fun getProjectById(
        @Path("id") id: Int,
    ): ProjectResponse

    @GET("participations/filter")
    suspend fun getParticipations(
        @Query("state") states: String =
            arrayListOf(ParticipationState.DISTRIBUTION.id)
                .joinToString(",", "[", "]"),
        @Query("pageSize") pageSize: String = "max",
    ): ParticipationsResponse

    @GET("specialities")
    suspend fun getSpecialties(): List<SpecialtyResponse>

    @GET("supervisors")
    suspend fun getSupervisors(): List<SupervisorDetailsResponse>
}
