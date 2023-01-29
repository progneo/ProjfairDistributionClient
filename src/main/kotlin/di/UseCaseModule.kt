package di

import dagger.Module
import dagger.Provides
import data.repository.StudentRepositoryImpl
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
        fun provideInsertStudentUseCase(studentRepositoryImpl: StudentRepositoryImpl): InsertStudentUseCase {
            return InsertStudentUseCase(
                studentRepositoryImpl = studentRepositoryImpl
            )
        }
    }
}