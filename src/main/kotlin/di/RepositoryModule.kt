package di

import dagger.Module
import dagger.Provides
import data.local.dao.*
import data.remote.api.AdminProjectFairApi
import data.remote.api.OrdinaryProjectFairApi
import data.repository.*
import domain.repository.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.math.log

@Module
interface RepositoryModule {

    companion object {

        @AppScope
        @Provides
        fun provideLoggingRepository(
            ioDispatcher: CoroutineDispatcher,
            logDao: LogDao,
        ): LoggingRepository {
            return LoggingRepositoryImpl(
                ioDispatcher = ioDispatcher,
                logDao = logDao
            )
        }

        @AppScope
        @Provides
        fun provideUploadDataRepository(
            ioDispatcher: CoroutineDispatcher,
            studentRepository: StudentRepository,
            projectRepository: ProjectRepository,
            participationRepository: ParticipationRepository,
            instituteRepository: InstituteRepository,
            departmentRepository: DepartmentRepository,
            supervisorRepository: SupervisorRepository,
            loggingRepository: LoggingRepository
        ): UploadDataRepository {
            return UploadDataRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentRepository = studentRepository,
                projectRepository = projectRepository,
                participationRepository = participationRepository,
                instituteRepository = instituteRepository,
                departmentRepository = departmentRepository,
                supervisorRepository = supervisorRepository,
                loggingRepository = loggingRepository
            )
        }

        @AppScope
        @Provides
        fun provideStudentRepository(
            ioDispatcher: CoroutineDispatcher,
            studentDao: StudentDao,
            projectFairApi: OrdinaryProjectFairApi,
            loggingRepository: LoggingRepository
        ): StudentRepository {
            return StudentRepositoryImpl(
                ioDispatcher = ioDispatcher,
                studentDao = studentDao,
                projectFairApi = projectFairApi,
                loggingRepository = loggingRepository
            )
        }

        @AppScope
        @Provides
        fun provideProjectRepository(
            ioDispatcher: CoroutineDispatcher,
            projectDao: ProjectDao,
            projectFairApi: OrdinaryProjectFairApi,
            loggingRepository: LoggingRepository
        ): ProjectRepository {
            return ProjectRepositoryImpl(
                ioDispatcher = ioDispatcher,
                projectDao = projectDao,
                projectFairApi = projectFairApi,
                loggingRepository = loggingRepository
            )
        }

        @AppScope
        @Provides
        fun provideParticipationRepository(
            ioDispatcher: CoroutineDispatcher,
            participationDao: ParticipationDao,
            ordinaryProjectFairApi: OrdinaryProjectFairApi,
            loggingRepository: LoggingRepository
        ): ParticipationRepository {
            return ParticipationRepositoryImpl(
                ioDispatcher = ioDispatcher,
                participationDao = participationDao,
                ordinaryProjectFairApi = ordinaryProjectFairApi,
                loggingRepository = loggingRepository
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

        @AppScope
        @Provides
        fun provideDepartmentRepository(
            ioDispatcher: CoroutineDispatcher,
            departmentDao: DepartmentDao,
            projectFairApi: OrdinaryProjectFairApi,
            adminProjectFairApi: AdminProjectFairApi
        ): DepartmentRepository {
            return DepartmentRepositoryImpl(
                ioDispatcher = ioDispatcher,
                departmentDao = departmentDao,
                projectFairApi = projectFairApi,
                adminProjectFairApi = adminProjectFairApi
            )
        }

        @AppScope
        @Provides
        fun provideSpecialtyRepository(
            ioDispatcher: CoroutineDispatcher,
            specialtyDao: SpecialtyDao,
            projectFairApi: OrdinaryProjectFairApi
        ): SpecialtyRepository {
            return SpecialtyRepositoryImpl(
                ioDispatcher = ioDispatcher,
                specialtyDao = specialtyDao,
                projectFairApi = projectFairApi
            )
        }

        @AppScope
        @Provides
        fun provideSupervisorRepository(
            ioDispatcher: CoroutineDispatcher,
            supervisorDao: SupervisorDao,
            projectFairApi: OrdinaryProjectFairApi
        ): SupervisorRepository {
            return SupervisorRepositoryImpl(
                ioDispatcher = ioDispatcher,
                supervisorDao = supervisorDao,
                projectFairApi = projectFairApi
            )
        }
    }
}