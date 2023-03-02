package data.remote.api

import data.dto.InstituteResponse
import retrofit2.http.GET

interface OrdinaryProjectFairApi {
    @GET("institutes")
    suspend fun getInstitutes(): List<InstituteResponse>
}