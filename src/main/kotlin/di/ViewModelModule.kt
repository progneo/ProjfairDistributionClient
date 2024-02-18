package di

import dagger.Module
import dagger.Provides
import domain.interactor.DownloadProgressInteractor
import domain.usecase.department.GetDepartmentsUseCase
import domain.usecase.department.UploadDepartmentsUseCase
import domain.usecase.institute.GetInstitutesUseCase
import domain.usecase.institute.UploadInstitutesUseCase
import domain.usecase.logging.GetLogsUseCase
import domain.usecase.logging.SaveLogUseCase
import domain.usecase.participation.*
import domain.usecase.project.*
import domain.usecase.specialty.GetSpecialtiesUseCase
import domain.usecase.student.*
import domain.usecase.supervisor.GetSupervisorsUseCase
import domain.usecase.supervisor.UploadSupervisorsUseCase
import domain.usecase.uploaddata.CancelOperationsUseCase
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
        @Preview
        @AppScope
        @Provides
        fun providePreviewUploadDataViewModel(
            @Preview syncDataUseCase: SyncDataUseCase,
            @Preview rebaseDataUseCase: RebaseDataUseCase,
            @Preview syncStudentsUseCase: SyncStudentsUseCase,
            @Preview rebaseStudentsUseCase: RebaseStudentsUseCase,
            @Preview syncProjectsUseCase: SyncProjectsUseCase,
            @Preview rebaseProjectsUseCase: RebaseProjectsUseCase,
            @Preview syncParticipationUseCase: SyncParticipationUseCase,
            @Preview rebaseParticipationUseCase: RebaseParticipationUseCase,
            uploadSupervisorsUseCase: UploadSupervisorsUseCase,
            uploadDepartmentsUseCase: UploadDepartmentsUseCase,
            uploadInstitutesUseCase: UploadInstitutesUseCase,
            @Preview cancelOperationsUseCase: CancelOperationsUseCase,
            @Preview downloadProgressInteractor: DownloadProgressInteractor,
        ): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase,
                rebaseDataUseCase = rebaseDataUseCase,
                syncStudentsUseCase = syncStudentsUseCase,
                rebaseStudentsUseCase = rebaseStudentsUseCase,
                syncProjectsUseCase = syncProjectsUseCase,
                rebaseProjectsUseCase = rebaseProjectsUseCase,
                syncParticipationUseCase = syncParticipationUseCase,
                rebaseParticipationUseCase = rebaseParticipationUseCase,
                uploadSupervisorsUseCase = uploadSupervisorsUseCase,
                uploadDepartmentsUseCase = uploadDepartmentsUseCase,
                uploadInstitutesUseCase = uploadInstitutesUseCase,
                cancelOperationsUseCase = cancelOperationsUseCase,
                downloadProgressInteractor = downloadProgressInteractor,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewUploadDataViewModel(
            @Review syncDataUseCase: SyncDataUseCase,
            @Review rebaseDataUseCase: RebaseDataUseCase,
            @Review syncStudentsUseCase: SyncStudentsUseCase,
            @Review rebaseStudentsUseCase: RebaseStudentsUseCase,
            @Review syncProjectsUseCase: SyncProjectsUseCase,
            @Review rebaseProjectsUseCase: RebaseProjectsUseCase,
            @Review syncParticipationUseCase: SyncParticipationUseCase,
            @Review rebaseParticipationUseCase: RebaseParticipationUseCase,
            uploadSupervisorsUseCase: UploadSupervisorsUseCase,
            uploadDepartmentsUseCase: UploadDepartmentsUseCase,
            uploadInstitutesUseCase: UploadInstitutesUseCase,
            @Review cancelOperationsUseCase: CancelOperationsUseCase,
            @Review downloadProgressInteractor: DownloadProgressInteractor,
        ): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase,
                rebaseDataUseCase = rebaseDataUseCase,
                syncStudentsUseCase = syncStudentsUseCase,
                rebaseStudentsUseCase = rebaseStudentsUseCase,
                syncProjectsUseCase = syncProjectsUseCase,
                rebaseProjectsUseCase = rebaseProjectsUseCase,
                syncParticipationUseCase = syncParticipationUseCase,
                rebaseParticipationUseCase = rebaseParticipationUseCase,
                uploadSupervisorsUseCase = uploadSupervisorsUseCase,
                uploadDepartmentsUseCase = uploadDepartmentsUseCase,
                uploadInstitutesUseCase = uploadInstitutesUseCase,
                cancelOperationsUseCase = cancelOperationsUseCase,
                downloadProgressInteractor = downloadProgressInteractor,
            )
        }

        @AppScope
        @Provides
        fun providePreviewViewModel(
            @Preview getStudentsUseCase: GetStudentsUseCase,
            @Preview getProjectsUseCase: GetProjectsUseCase,
            @Preview updateProjectUseCase: UpdateProjectUseCase,
            @Preview getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            getDepartmentsUseCase: GetDepartmentsUseCase,
            getSpecialtiesUseCase: GetSpecialtiesUseCase,
            getSupervisorsUseCase: GetSupervisorsUseCase,
            @Preview syncProjectUseCase: SyncProjectUseCase,
            getLogsUseCase: GetLogsUseCase,
            saveLogUseCase: SaveLogUseCase,
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
                saveLogUseCase = saveLogUseCase,
            )
        }

        @AppScope
        @Provides
        fun provideReviewViewModel(
            @Review getStudentsUseCase: GetStudentsUseCase,
            @Review getProjectsUseCase: GetProjectsUseCase,
            @Review updateProjectUseCase: UpdateProjectUseCase,
            @Review getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            getDepartmentsUseCase: GetDepartmentsUseCase,
            getSpecialtiesUseCase: GetSpecialtiesUseCase,
            getSupervisorsUseCase: GetSupervisorsUseCase,
            @Review syncProjectUseCase: SyncProjectUseCase,
            getLogsUseCase: GetLogsUseCase,
            saveLogUseCase: SaveLogUseCase,
            insertParticipationOnServerUseCase: InsertParticipationOnServerUseCase,
            updateProjectsOnServerUseCase: UpdateProjectsOnServerUseCase,
            updateParticipationOnServerUseCase: UpdateParticipationOnServerUseCase,
            getParticipationLastIndexUseCase: GetParticipationLastIndexUseCase,
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
                insertParticipationOnServerUseCase = insertParticipationOnServerUseCase,
                updateProjectsOnServerUseCase = updateProjectsOnServerUseCase,
                updateParticipationOnServerUseCase = updateParticipationOnServerUseCase,
                getParticipationLastIndexUseCase = getParticipationLastIndexUseCase,
            )
        }

        @AppScope
        @Provides
        fun provideAlgorithmViewModel(
            @Preview getStudentsUseCase: GetStudentsUseCase,
            @Preview getProjectsUseCase: GetProjectsUseCase,
            @Preview getParticipationsUseCase: GetParticipationsUseCase,
            getInstitutesUseCase: GetInstitutesUseCase,
            insertStudentUseCase: InsertStudentUseCase,
            insertProjectsUseCase: InsertProjectsUseCase,
            insertParticipationsUseCase: InsertParticipationsUseCase,
            deleteAllStudentsUseCase: DeleteAllStudentsUseCase,
            deleteAllProjectsUseCase: DeleteAllProjectsUseCase,
            deleteAllParticipationsUseCase: DeleteAllParticipationsUseCase,
        ): AlgorithmViewModel {
            return AlgorithmViewModel(
                getStudentsUseCase = getStudentsUseCase,
                getProjectsUseCase = getProjectsUseCase,
                getParticipationsUseCase = getParticipationsUseCase,
                getInstitutesUseCase = getInstitutesUseCase,
                insertStudentUseCase = insertStudentUseCase,
                insertProjectsUseCase = insertProjectsUseCase,
                insertParticipationsUseCase = insertParticipationsUseCase,
                deleteAllStudentsUseCase = deleteAllStudentsUseCase,
                deleteAllProjectsUseCase = deleteAllProjectsUseCase,
                deleteAllParticipationsUseCase = deleteAllParticipationsUseCase,
            )
        }

        @Preview
        @Provides
        fun providePreviewParticipationDetailsViewModel(
            @Preview getParticipationsUseCase: GetParticipationsUseCase,
            @Preview getStudentsUseCase: GetStudentsUseCase,
            @Preview updateParticipationUseCase: UpdateParticipationUseCase,
            @Preview deleteParticipationsUseCase: DeleteParticipationUseCase,
        ): ParticipationDetailsViewModel {
            return ParticipationDetailsViewModel(
                getParticipationsUseCase = getParticipationsUseCase,
                getStudentsUseCase = getStudentsUseCase,
                updateParticipationUseCase = updateParticipationUseCase,
                deleteParticipationUseCase = deleteParticipationsUseCase,
            )
        }

        @Review
        @Provides
        fun provideReviewParticipationDetailsViewModel(
            @Review getParticipationsUseCase: GetParticipationsUseCase,
            @Review getStudentsUseCase: GetStudentsUseCase,
            @Review updateParticipationUseCase: UpdateParticipationUseCase,
            @Review deleteParticipationsUseCase: DeleteParticipationUseCase,
        ): ParticipationDetailsViewModel {
            return ParticipationDetailsViewModel(
                getParticipationsUseCase = getParticipationsUseCase,
                getStudentsUseCase = getStudentsUseCase,
                updateParticipationUseCase = updateParticipationUseCase,
                deleteParticipationUseCase = deleteParticipationsUseCase,
            )
        }
    }
}
