package di

import dagger.Module
import dagger.Provides
import data.repository.StudentRepositoryImpl
import data.repository.UploadDataRepositoryImpl
import domain.repository.UploadDataRepository
import kotlinx.coroutines.CoroutineDispatcher

@Module
interface RepositoryModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataRepository(
            ioDispatcher: CoroutineDispatcher
        ): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher
            )
        }

        @AppScope
        @Provides
        fun provideStudentRepository(
            ioDispatcher: CoroutineDispatcher
        ): StudentRepositoryImpl {
            return StudentRepositoryImpl(
                ioDispatcher = ioDispatcher
            )
        }
    }
}