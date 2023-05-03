package di

import dagger.Module

@Module
interface ViewModelModule {

//    companion object {
//
//        @AppScope
//        @Provides
//        fun provideUploadDataViewModel(
//            syncDataUseCase: SyncDataUseCase,
//            uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase,
//            downloadProgressInteractor: DownloadProgressInteractor
//        ): UploadDataViewModel {
//            return UploadDataViewModel(
//                syncDataUseCase = syncDataUseCase,
//                uploadExceptionalStudentsUseCase = uploadExceptionalStudentsUseCase,
//                downloadProgressInteractor = downloadProgressInteractor
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun providePreviewViewModel(
//            getStudentsUseCase: GetStudentsUseCase,
//            getProjectsUseCase: GetProjectsUseCase,
//            updateProjectUseCase: UpdateProjectUseCase,
//            getParticipationsUseCase: GetParticipationsUseCase,
//            getInstitutesUseCase: GetInstitutesUseCase,
//            getDepartmentsUseCase: GetDepartmentsUseCase,
//            getSpecialtiesUseCase: GetSpecialtiesUseCase,
//            getSupervisorsUseCase: GetSupervisorsUseCase,
//
//        ): PreviewViewModel {
//            return PreviewViewModel(
//                getStudentsUseCase = getStudentsUseCase,
//                getProjectsUseCase = getProjectsUseCase,
//                updateProjectUseCase = updateProjectUseCase,
//                getParticipationsUseCase = getParticipationsUseCase,
//                getInstitutesUseCase = getInstitutesUseCase,
//                getDepartmentsUseCase = getDepartmentsUseCase,
//                getSpecialtiesUseCase = getSpecialtiesUseCase,
//                getSupervisorsUseCase = getSupervisorsUseCase,
//            )
//        }
//
//        @AppScope
//        @Provides
//        fun provideAlgorithmViewModel(
//            getStudentsUseCase: GetStudentsUseCase,
//            getProjectsUseCase: GetProjectsUseCase,
//            getParticipationsUseCase: GetParticipationsUseCase,
//            getInstitutesUseCase: GetInstitutesUseCase,
//            getDepartmentsUseCase: GetDepartmentsUseCase,
//            getSpecialtiesUseCase: GetSpecialtiesUseCase,
//            getSupervisorsUseCase: GetSupervisorsUseCase
//        ): AlgorithmViewModel {
//            return AlgorithmViewModel(
//                getStudentsUseCase = getStudentsUseCase,
//                getProjectsUseCase = getProjectsUseCase,
//                getParticipationsUseCase = getParticipationsUseCase,
//                getInstitutesUseCase = getInstitutesUseCase,
//                getDepartmentsUseCase = getDepartmentsUseCase,
//                getSpecialtiesUseCase = getSpecialtiesUseCase,
//                getSupervisorsUseCase = getSupervisorsUseCase
//            )
//        }
//    }
}