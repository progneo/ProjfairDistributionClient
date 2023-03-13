package di

import dagger.Module
import dagger.Provides
import domain.interactor.DownloadProgressInteractor
import domain.repository.UploadDataRepository

@Module
interface InteractorModule {

    companion object {

        @AppScope
        @Provides
        fun provideDownloadProgressInteractor(uploadDataRepository: UploadDataRepository): DownloadProgressInteractor {
            return DownloadProgressInteractor(
                uploadDataRepository = uploadDataRepository
            )
        }
    }
}