package di

import dagger.Module
import dagger.Provides
import domain.repository.*
import domain.usecase.participation.*
import domain.usecase.project.*
import domain.usecase.student.GetStudentsUseCase
import domain.usecase.student.RebaseStudentsUseCase
import domain.usecase.student.SyncStudentsUseCase
import domain.usecase.uploaddata.CancelOperationsUseCase
import domain.usecase.uploaddata.RebaseDataUseCase
import domain.usecase.uploaddata.SyncDataUseCase

@Module
interface UseCaseModule {
    companion object {
        // ----------------------------------------
        // -----------------------------------------STUDENTS
        // ----------------------------------------

        @Preview
        @AppScope
        @Provides
        fun provideGetPreviewStudentsUseCase(
            @Preview studentRepository: StudentRepository,
        ): GetStudentsUseCase {
            return GetStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideGetReviewStudentsUseCase(
            @Review studentRepository: StudentRepository,
        ): GetStudentsUseCase {
            return GetStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideRebasePreviewStudentsUseCase(
            @Preview studentRepository: StudentRepository,
        ): RebaseStudentsUseCase {
            return RebaseStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideRebaseReviewStudentsUseCase(
            @Review studentRepository: StudentRepository,
        ): RebaseStudentsUseCase {
            return RebaseStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideSyncPreviewStudentsUseCase(
            @Preview studentRepository: StudentRepository,
        ): SyncStudentsUseCase {
            return SyncStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideSyncReviewStudentsUseCase(
            @Review studentRepository: StudentRepository,
        ): SyncStudentsUseCase {
            return SyncStudentsUseCase(
                studentRepository = studentRepository,
            )
        }

        // ----------------------------------------
        // -----------------------------------------PROJECTS
        // ----------------------------------------

        @Preview
        @AppScope
        @Provides
        fun provideGetPreviewProjectsUseCase(
            @Preview projectRepository: ProjectRepository,
        ): GetProjectsUseCase {
            return GetProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideGetReviewProjectsUseCase(
            @Review projectRepository: ProjectRepository,
        ): GetProjectsUseCase {
            return GetProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideRebasePreviewProjectsUseCase(
            @Preview projectRepository: ProjectRepository,
        ): RebaseProjectsUseCase {
            return RebaseProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideRebaseReviewProjectsUseCase(
            @Review projectRepository: ProjectRepository,
        ): RebaseProjectsUseCase {
            return RebaseProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideSyncPreviewProjectsUseCase(
            @Preview projectRepository: ProjectRepository,
        ): SyncProjectsUseCase {
            return SyncProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideSyncReviewProjectsUseCase(
            @Review projectRepository: ProjectRepository,
        ): SyncProjectsUseCase {
            return SyncProjectsUseCase(
                projectRepository = projectRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideSyncPreviewProjectUseCase(
            @Preview projectRepository: ProjectRepository,
        ): SyncProjectUseCase {
            return SyncProjectUseCase(
                projectRepository = projectRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideSyncReviewProjectUseCase(
            @Review projectRepository: ProjectRepository,
        ): SyncProjectUseCase {
            return SyncProjectUseCase(
                projectRepository = projectRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideUpdatePreviewProjectUseCase(
            @Preview projectRepository: ProjectRepository,
        ): UpdateProjectUseCase {
            return UpdateProjectUseCase(
                projectRepository = projectRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideUpdateReviewProjectUseCase(
            @Review projectRepository: ProjectRepository,
        ): UpdateProjectUseCase {
            return UpdateProjectUseCase(
                projectRepository = projectRepository,
            )
        }

        // ----------------------------------------
        // ----------------------------------------PARTICIPATION
        // ----------------------------------------

        @Preview
        @AppScope
        @Provides
        fun provideGetPreviewParticipationsUseCase(
            @Preview participationRepository: ParticipationRepository,
        ): GetParticipationsUseCase {
            return GetParticipationsUseCase(
                participationRepository = participationRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideGetReviewParticipationsUseCase(
            @Review participationRepository: ParticipationRepository,
        ): GetParticipationsUseCase {
            return GetParticipationsUseCase(
                participationRepository = participationRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideRebasePreviewParticipationsUseCase(
            @Preview participationRepository: ParticipationRepository,
        ): RebaseParticipationUseCase {
            return RebaseParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideRebaseReviewParticipationsUseCase(
            @Review participationRepository: ParticipationRepository,
        ): RebaseParticipationUseCase {
            return RebaseParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideSyncPreviewParticipationsUseCase(
            @Preview participationRepository: ParticipationRepository,
        ): SyncParticipationUseCase {
            return SyncParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideSyncReviewParticipationsUseCase(
            @Review participationRepository: ParticipationRepository,
        ): SyncParticipationUseCase {
            return SyncParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideUpdatePreviewParticipationsUseCase(
            @Preview participationRepository: ParticipationRepository,
        ): UpdateParticipationUseCase {
            return UpdateParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideUpdateReviewParticipationsUseCase(
            @Review participationRepository: ParticipationRepository,
        ): UpdateParticipationUseCase {
            return UpdateParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun provideDeletePreviewParticipationUseCase(
            @Preview participationRepository: ParticipationRepository,
        ): DeleteParticipationUseCase {
            return DeleteParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideDeleteReviewParticipationUseCase(
            @Review participationRepository: ParticipationRepository,
        ): DeleteParticipationUseCase {
            return DeleteParticipationUseCase(
                participationRepository = participationRepository,
            )
        }

        // ----------------------------------------
        // -----------------------------------------UPLOAD DATA
        // ----------------------------------------

        @Preview
        @AppScope
        @Provides
        fun providePreviewCancelOperationUseCase(
            @Preview uploadDataRepository: UploadDataRepository,
        ): CancelOperationsUseCase {
            return CancelOperationsUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewCancelOperationUseCase(
            @Review uploadDataRepository: UploadDataRepository,
        ): CancelOperationsUseCase {
            return CancelOperationsUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun providePreviewRebaseDataUseCase(
            @Preview uploadDataRepository: UploadDataRepository,
        ): RebaseDataUseCase {
            return RebaseDataUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewRebaseDataUseCase(
            @Review uploadDataRepository: UploadDataRepository,
        ): RebaseDataUseCase {
            return RebaseDataUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }

        @Preview
        @AppScope
        @Provides
        fun providePreviewSyncDataUseCase(
            @Preview uploadDataRepository: UploadDataRepository,
        ): SyncDataUseCase {
            return SyncDataUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewSyncDataUseCase(
            @Review uploadDataRepository: UploadDataRepository,
        ): SyncDataUseCase {
            return SyncDataUseCase(
                uploadDataRepository = uploadDataRepository,
            )
        }
    }
}
