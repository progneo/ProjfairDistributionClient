package di

import dagger.Module
import dagger.Provides
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import ru.student.distribution.domain.repository.UploadDataRepository

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
    }
}