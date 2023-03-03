package di

import dagger.Module
import dagger.Provides
import domain.repository.*
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.student.InsertStudentUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase

@Module
interface UseCaseModule {

    companion object {

        @AppScope
        @Provides
        fun provideSyncDataUseCase(uploadDataRepository: UploadDataRepository): SyncDataUseCase {
            return SyncDataUseCase(
                uploadDataRepository = uploadDataRepository
            )
        }

        @AppScope
        @Provides
        fun provideUploadExceptionalStudentsUseCase(uploadDataRepository: UploadDataRepository): UploadExceptionalStudentsUseCase {
            return UploadExceptionalStudentsUseCase(
                uploadDataRepository = uploadDataRepository
            )
        }

        @AppScope
        @Provides
        fun provideInsertStudentUseCase(studentRepository: StudentRepository): InsertStudentUseCase {
            return InsertStudentUseCase(
                studentRepository = studentRepository
            )
        }

        @AppScope
        @Provides
        fun provideGetStudentsUseCase(studentRepository: StudentRepository): GetStudentsUseCase {
            return GetStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @AppScope
        @Provides
        fun provideGetProjectsUseCase(projectRepository: ProjectRepository): GetProjectsUseCase {
            return GetProjectsUseCase(
                projectRepository = projectRepository
            )
        }

        @AppScope
        @Provides
        fun provideGetParticipationsUseCase(participationRepository: ParticipationRepository): GetParticipationsUseCase {
            return GetParticipationsUseCase(
                participationRepository = participationRepository
            )
        }

        @AppScope
        @Provides
        fun provideGetInstitutesUseCase(instituteRepository: InstituteRepository): GetInstitutesUseCase {
            return GetInstitutesUseCase(
                instituteRepository = instituteRepository
            )
        }
    }
}