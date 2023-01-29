package di

import dagger.Module
import dagger.Provides
import data.local.dao.StudentDao
import data.repository.StudentRepositoryImpl
import data.repository.UploadDataRepositoryImpl
import domain.repository.StudentRepository
import domain.repository.UploadDataRepository
import kotlinx.coroutines.CoroutineDispatcher

@Module
interface RepositoryModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataRepository(
            ioDispatcher: CoroutineDispatcher,
            studentRepository: StudentRepository
        ): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentRepository = studentRepository
            )
        }

        @AppScope
        @Provides
        fun provideStudentRepository(
            ioDispatcher: CoroutineDispatcher,
            studentDao: StudentDao
        ): StudentRepository {
            return StudentRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentDao = studentDao
            )
        }
    }
}