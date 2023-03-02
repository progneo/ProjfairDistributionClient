package di

import dagger.Module
import dagger.Provides
import data.local.dao.ProjectDao
import data.local.dao.StudentDao
import data.remote.api.AdminProjectFairApi
import data.repository.ProjectRepositoryImpl
import data.repository.StudentRepositoryImpl
import data.repository.UploadDataRepositoryImpl
import domain.repository.ProjectRepository
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
            studentRepository: StudentRepository,
            projectRepository: ProjectRepository
        ): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentRepository = studentRepository,
                projectRepository = projectRepository
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

        @AppScope
        @Provides
        fun provideProjectRepository(
            ioDispatcher: CoroutineDispatcher,
            projectDao: ProjectDao,
            adminProjectFairApi: AdminProjectFairApi
        ): ProjectRepository {
            return ProjectRepositoryImpl(
                ioDispatcher = ioDispatcher,
                projectDao = projectDao,
                adminProjectFairApi = adminProjectFairApi
            )
        }
    }
}