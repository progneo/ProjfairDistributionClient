package ru.student.distribution.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.student.distribution.data.repository.UploadDataRepositoryImpl
import ru.student.distribution.domain.repository.UploadDataRepository

@Module
interface CoroutineDispatcherModule {

    companion object {

        @AppScope
        @Provides
        fun provideIoDispatcher(): CoroutineDispatcher {
            return Dispatchers.IO
        }
    }
}