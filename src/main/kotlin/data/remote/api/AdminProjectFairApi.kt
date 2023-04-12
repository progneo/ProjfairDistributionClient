package data.remote.api

import domain.model.Department
import retrofit2.http.GET

interface AdminProjectFairApi {

    @GET("/departments")
    suspend fun getDepartments(): List<Department>
}