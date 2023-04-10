package di

import dagger.Module
import dagger.Provides
import data.local.dao.InstituteDao
import data.local.dao.ParticipationDao
import data.local.dao.ProjectDao
import data.local.dao.StudentDao
import data.remote.api.OrdinaryProjectFairApi
import data.repository.*
import domain.repository.*
import kotlinx.coroutines.CoroutineDispatcher

@Module
interface RepositoryModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataRepository(
            ioDispatcher: CoroutineDispatcher,
            studentRepository: StudentRepository,
            projectRepository: ProjectRepository,
            participationRepository: ParticipationRepository,
            instituteRepository: InstituteRepository,
        ): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentRepository = studentRepository,
                projectRepository = projectRepository,
                participationRepository = participationRepository,
                instituteRepository = instituteRepository
            )
        }

        @AppScope
        @Provides
        fun provideStudentRepository(
            ioDispatcher: CoroutineDispatcher,
            studentDao: StudentDao,
            projectFairApi: OrdinaryProjectFairApi,
        ): StudentRepository {
            return StudentRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentDao = studentDao,
                projectFairApi = projectFairApi
            )
        }

        @AppScope
        @Provides
        fun provideProjectRepository(
            ioDispatcher: CoroutineDispatcher,
            projectDao: ProjectDao,
            projectFairApi: OrdinaryProjectFairApi,
        ): ProjectRepository {
            return ProjectRepositoryImpl(
                ioDispatcher = ioDispatcher,
                projectDao = projectDao,
                projectFairApi = projectFairApi
            )
        }

        @AppScope
        @Provides
        fun provideParticipationRepository(
            ioDispatcher: CoroutineDispatcher,
            participationDao: ParticipationDao,
            ordinaryProjectFairApi: OrdinaryProjectFairApi,
        ): ParticipationRepository {
            return ParticipationRepositoryImpl(
                ioDispatcher = ioDispatcher,
                participationDao = participationDao,
                ordinaryProjectFairApi = ordinaryProjectFairApi
            )
        }

        @AppScope
        @Provides
        fun provideInstituteRepository(
            ioDispatcher: CoroutineDispatcher,
            instituteDao: InstituteDao,
            projectFairApi: OrdinaryProjectFairApi
        ): InstituteRepository {
            return InstituteRepositoryImpl(
                ioDispatcher = ioDispatcher,
                instituteDao = instituteDao,
                projectFairApi = projectFairApi
            )
        }
    }
}