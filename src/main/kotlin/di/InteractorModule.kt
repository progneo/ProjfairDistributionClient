package di

import dagger.Module
import dagger.Provides
import domain.interactor.DownloadProgressInteractor
import domain.repository.UploadDataRepository

@Module
interface InteractorModule {

    companion object {

        @Preview
        @AppScope
        @Provides
        fun providePreviewDownloadProgressInteractor(@Preview uploadDataRepository: UploadDataRepository): DownloadProgressInteractor {
            return DownloadProgressInteractor(
                uploadDataRepository = uploadDataRepository
            )
        }

        @Review
        @AppScope
        @Provides
        fun provideReviewDownloadProgressInteractor(@Review uploadDataRepository: UploadDataRepository): DownloadProgressInteractor {
            return DownloadProgressInteractor(
                uploadDataRepository = uploadDataRepository
            )
        }
    }
}