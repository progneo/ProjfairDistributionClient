package di

import dagger.Module
import dagger.Provides
import domain.interactor.DownloadProgressInteractor
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.preview.viewmodel.PreviewViewModel
import ui.uploaddata.viewmodel.UploadDataViewModel

@Module
interface ViewModelModule {

    companion object {

        @AppScope
        @Provides
        fun provideUploadDataViewModel(
            syncDataUseCase: SyncDataUseCase,
            uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase,
            downloadProgressInteractor: DownloadProgressInteractor
        ): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase,
                uploadExceptionalStudentsUseCase = uploadExceptionalStudentsUseCase,
                downloadProgressInteractor = downloadProgressInteractor
            )
        }

        @AppScope
        @Provides
        fun providePreviewViewModel(
            getStudentsUseCase: GetStudentsUseCase,
            getProjectsUseCase: GetProjectsUseCase,
            updateProjectUseCase: UpdateProjectUseCase,
            getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            getDepartmentsUseCase: GetDepartmentsUseCase,
            getSpecialtiesUseCase: GetSpecialtiesUseCase,
            getSupervisorsUseCase: GetSupervisorsUseCase

        ): PreviewViewModel {
            return PreviewViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                updateProjectUseCase = updateProjectUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                getDepartmentsUseCase = getDepartmentsUseCase,
                getSpecialtiesUseCase = getSpecialtiesUseCase,
                getSupervisorsUseCase = getSupervisorsUseCase
            )
        }

        @AppScope
        @Provides
        fun provideAlgorithmViewModel(
            getStudentsUseCase: GetStudentsUseCase,
            getProjectsUseCase: GetProjectsUseCase,
            getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            getDepartmentsUseCase: GetDepartmentsUseCase,
            getSpecialtiesUseCase: GetSpecialtiesUseCase,
            getSupervisorsUseCase: GetSupervisorsUseCase

        ): AlgorithmViewModel {
            return AlgorithmViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                getDepartmentsUseCase = getDepartmentsUseCase,
                getSpecialtiesUseCase = getSpecialtiesUseCase,
                getSupervisorsUseCase = getSupervisorsUseCase
            )
        }
    }
}