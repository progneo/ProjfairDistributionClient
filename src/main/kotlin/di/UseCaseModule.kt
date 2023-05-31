package di

import dagger.Module
import dagger.Provides
import domain.repository.*
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.student.RebaseStudentsUseCase
import domain.usecase.student.SyncStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import domain.usecase.uploaddata.SyncDataUseCase

@Module
interface UseCaseModule {

    companion object {

//        @AppScope
//        @Provides
//        fun provideSyncDataUseCase(uploadDataRepository: UploadDataRepository): SyncDataUseCase {
//            return SyncDataUseCase(
//                uploadDataRepository = uploadDataRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideUploadExceptionalStudentsUseCase(uploadDataRepository: UploadDataRepository): UploadExceptionalStudentsUseCase {
//            return UploadExceptionalStudentsUseCase(
//                uploadDataRepository = uploadDataRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideInsertStudentUseCase(studentRepository: StudentRepository): InsertStudentUseCase {
//            return InsertStudentUseCase(
//                studentRepository = studentRepository
//            )
//        }
//
        @Preview
        @AppScope
        @Provides
        fun provideGetPreviewStudentsUseCase(@Preview studentRepository: StudentRepository): GetStudentsUseCase {
            return GetStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideGetReviewStudentsUseCase(@Review studentRepository: StudentRepository): GetStudentsUseCase {
            return GetStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideRebasePreviewStudentsUseCase(@Preview studentRepository: StudentRepository): RebaseStudentsUseCase {
            return RebaseStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideRebaseReviewStudentsUseCase(@Review studentRepository: StudentRepository): RebaseStudentsUseCase {
            return RebaseStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideSyncPreviewStudentsUseCase(@Preview studentRepository: StudentRepository): SyncStudentsUseCase {
            return SyncStudentsUseCase(
                studentRepository = studentRepository
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideSyncReviewStudentsUseCase(@Review studentRepository: StudentRepository): SyncStudentsUseCase {
            return SyncStudentsUseCase(
                studentRepository = studentRepository
            )
        }
//
//        @AppScope
//        @Provides
//        fun provideGetProjectsUseCase(projectRepository: ProjectRepository): GetProjectsUseCase {
//            return GetProjectsUseCase(
//                projectRepository = projectRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideGetParticipationsUseCase(participationRepository: ParticipationRepository): GetParticipationsUseCase {
//            return GetParticipationsUseCase(
//                participationRepository = participationRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideGetInstitutesUseCase(instituteRepository: InstituteRepository): GetInstitutesUseCase {
//            return GetInstitutesUseCase(
//                instituteRepository = instituteRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideGetSpecialtiesUseCase(specialtyRepository: SpecialtyRepository): GetSpecialtiesUseCase {
//            return GetSpecialtiesUseCase(
//                specialtyRepository = specialtyRepository
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideGetSupervisorsUseCase(supervisorRepository: SupervisorRepository): GetSupervisorsUseCase {
//            return GetSupervisorsUseCase(
//                supervisorRepository = supervisorRepository
//            )
//        }
    }
}