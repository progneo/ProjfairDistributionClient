package ru.student.distribution.data.remote.client

import ru.student.distribution.data.remote.api.ProjectFairApi

object ProjectFairClient {

    @Synchronized
    fun getClient(): ProjectFairApi = BaseClient.getRetrofitClient().create(ProjectFairApi::class.java)
}