package di

import dagger.Module

@Module
interface UseCaseModule {

//    companion object {
//
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
//        @AppScope
//        @Provides
//        fun provideGetStudentsUseCase(studentRepository: StudentRepository): GetStudentsUseCase {
//            return GetStudentsUseCase(
//                studentRepository = studentRepository
//            )
//        }
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
//    }
}