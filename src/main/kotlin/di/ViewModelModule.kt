package di

import dagger.Module
import dagger.Provides
import domain.usecase.uploaddata.SyncDataUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import uploaddata.viewmodel.UploadDataViewModel

@Module
interface ViewModelModule {

    companion object {


        @Provides
        fun provideUploadDataViewModel(
            syncDataUseCase: SyncDataUseCase,
            uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase
        ): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase,
                uploadExceptionalStudentsUseCase = uploadExceptionalStudentsUseCase
            )
        }
    }
}