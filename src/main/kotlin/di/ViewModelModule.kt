package di

import dagger.Module
import dagger.Provides
import domain.interactor.DownloadProgressInteractor
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.file.GetGeneratedDistributionUseCase
import domain.usecase.file.SaveGeneratedDistributionUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.logging.GetLogsUseCase
import domain.usecase.logging.SaveLogUseCase
import domain.usecase.participation.GetParticipationsUseCase
import domain.usecase.project.GetProjectsUseCase
import domain.usecase.project.SyncProjectUseCase
import domain.usecase.project.UpdateProjectUseCase
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.supervisor.GetSupervisorsUseCase
import domain.usecase.uploaddata.RebaseDataUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.preview.viewmodel.PreviewViewModel
import ui.review.viewmodel.ReviewViewModel
import ui.uploaddata.viewmodel.UploadDataViewModel

@Module
interface ViewModelModule {

    companion object {

//        @AppScope
//        @Provides
//        fun provideUploadDataViewModel(
//            syncDataUseCase: SyncDataUseCase,
//            rebaseDataUseCase: RebaseDataUseCase,
//            downloadProgressInteractor: DownloadProgressInteractor,
//        ): UploadDataViewModel {
//            return UploadDataViewModel(
//                syncDataUseCase = syncDataUseCase,
//                rebaseDataUseCase = rebaseDataUseCase,
//                downloadProgressInteractor = downloadProgressInteractor
//            )
//        }

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
            getSupervisorsUseCase: GetSupervisorsUseCase,
            syncProjectUseCase: SyncProjectUseCase,
            getLogsUseCase: GetLogsUseCase,
            saveLogUseCase: SaveLogUseCase
        ): PreviewViewModel {
            return PreviewViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                updateProjectUseCase = updateProjectUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                getDepartmentsUseCase = getDepartmentsUseCase,
                getSpecialtiesUseCase = getSpecialtiesUseCase,
                getSupervisorsUseCase = getSupervisorsUseCase,
                syncProjectUseCase = syncProjectUseCase,
                getLogsUseCase = getLogsUseCase,
                saveLogUseCase = saveLogUseCase
            )
        }

        @AppScope
        @Provides
        fun provideReviewViewModel(
            getStudentsUseCase: GetStudentsUseCase,
            getProjectsUseCase: GetProjectsUseCase,
            updateProjectUseCase: UpdateProjectUseCase,
            getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            getDepartmentsUseCase: GetDepartmentsUseCase,
            getSpecialtiesUseCase: GetSpecialtiesUseCase,
            getSupervisorsUseCase: GetSupervisorsUseCase,
            syncProjectUseCase: SyncProjectUseCase,
            getLogsUseCase: GetLogsUseCase,
            saveLogUseCase: SaveLogUseCase,
            getGeneratedDistributionUseCase: GetGeneratedDistributionUseCase
        ): ReviewViewModel {
            return ReviewViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                updateProjectUseCase = updateProjectUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                getDepartmentsUseCase = getDepartmentsUseCase,
                getSpecialtiesUseCase = getSpecialtiesUseCase,
                getSupervisorsUseCase = getSupervisorsUseCase,
                syncProjectUseCase = syncProjectUseCase,
                getLogsUseCase = getLogsUseCase,
                saveLogUseCase = saveLogUseCase,
                getGeneratedDistributionUseCase = getGeneratedDistributionUseCase
            )
        }

        @AppScope
        @Provides
        fun provideAlgorithmViewModel(
            getStudentsUseCase: GetStudentsUseCase,
            getProjectsUseCase: GetProjectsUseCase,
            getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            saveGeneratedDistributionUseCase: SaveGeneratedDistributionUseCase
        ): AlgorithmViewModel {
            return AlgorithmViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                saveGeneratedDistributionUseCase = saveGeneratedDistributionUseCase
            )
        }

//        @AppScope
//        @Provides
//        fun provideParticipationDetailsViewModel(): ParticipationDetailsViewModel {
//            return ParticipationDetailsViewModel()
//        }
    }
}