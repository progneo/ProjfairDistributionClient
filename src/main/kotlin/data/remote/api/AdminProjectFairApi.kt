package data.remote.api

import data.dto.DepartmentResponse
import retrofit2.http.GET

interface AdminProjectFairApi {

    @GET("departments")
    suspend fun getDepartments(): List<DepartmentResponse>
}