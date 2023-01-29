package di

import dagger.Module
import dagger.Provides
import domain.repository.UploadDataRepository
import domain.usecase.student.InsertStudentUseCase
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase

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

        @AppScope
        @Provides
        fun provideInsertStudentUseCase(uploadDataRepository: UploadDataRepository): InsertStudentUseCase {
            return InsertStudentUseCase(
                uploadDataRepository = uploadDataRepository
            )
        }
    }
}