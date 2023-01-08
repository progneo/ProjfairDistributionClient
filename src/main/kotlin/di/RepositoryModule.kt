package ru.student.distribution.di

import dagger.Module
import dagger.Provides
import di.AppScope
import kotlinx.coroutines.CoroutineDispatcher
import data.repository.UploadDataRepositoryImpl
import ru.student.distribution.domain.repository.UploadDataRepository

@Module
interface RepositoryModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataRepository(ioDispatcher: CoroutineDispatcher): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher
            )
        }
    }
}