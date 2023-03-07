package data.remote.api

import data.dto.InstituteResponse
import data.dto.ProjectsResponse
import data.dto.StudentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdinaryProjectFairApi {
    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>

    @GET("candidates")
    suspend fun getCandidates(): List<StudentResponse>

//    @GET("projects/filter")
//    suspend fun getProjects(
//        @Query ("pageSize") pageSize: String = "max"
//    ): List<ProjectResponse>

    @GET("projects/filter")
    suspend fun getProjects(
        //@Query ("pageSize") pageSize: String = "max"
    ): ProjectsResponse
}