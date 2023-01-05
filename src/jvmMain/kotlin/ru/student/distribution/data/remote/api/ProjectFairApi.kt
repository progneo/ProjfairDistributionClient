package ru.student.distribution.data.remote.api

import ru.student.distribution.data.dto.ProjectsResponse
import retrofit2.http.GET

interface ProjectFairApi {
    @GET("projects")
    suspend fun getProjects(): ProjectsResponse
}