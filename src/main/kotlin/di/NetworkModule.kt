package di

import dagger.Module
import dagger.Provides
import data.remote.api.AdminProjectFairApi
import data.remote.api.OrdinaryProjectFairApi
import data.remote.client.ProjectFairClient

@Module
interface NetworkModule {

    companion object {

        @AppScope
        @Provides
        fun provideOrdinaryProjectFairApi(): OrdinaryProjectFairApi {
            return ProjectFairClient.getOrdinaryClient()
        }

        @AppScope
        @Provides
        fun provideAdminProjectFairApi(): AdminProjectFairApi {
            return ProjectFairClient.getAdminClient()
        }
    }
}