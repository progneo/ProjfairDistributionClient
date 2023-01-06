package ru.student.distribution.di

import dagger.Module
import dagger.Provides
import di.AppScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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