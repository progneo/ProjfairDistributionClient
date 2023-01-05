package ru.student.distribution.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import ru.student.distribution.data.repository.UploadDataRepositoryImpl
import ru.student.distribution.domain.repository.UploadDataRepository
import ru.student.distribution.domain.usecase.uploaddata.SyncDataUseCase
import ru.student.distribution.ui.uploaddata.UploadDataViewModel

@Module
interface ViewModelModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataViewModel(syncDataUseCase: SyncDataUseCase): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase
            )
        }
    }
}