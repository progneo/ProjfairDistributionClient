package data.remote.client

import data.remote.api.AdminProjectFairApi
import data.remote.api.OrdinaryProjectFairApi

object ProjectFairClient {

    @Synchronized
    fun getOrdinaryClient(): OrdinaryProjectFairApi = OrdinaryClient.getRetrofitClient().create(OrdinaryProjectFairApi::class.java)

    @Synchronized
    fun getAdminClient(): AdminProjectFairApi = AdminClient.getRetrofitClient().create(AdminProjectFairApi::class.java)
}