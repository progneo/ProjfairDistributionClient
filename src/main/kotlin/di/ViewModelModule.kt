package di

import dagger.Module
import dagger.Provides
import ru.student.distribution.domain.usecase.uploaddata.SyncDataUseCase
import uploaddata.UploadDataViewModel

@Module
interface ViewModelModule {

    companion object {


        @Provides
        fun provideUploadDataViewModel(syncDataUseCase: SyncDataUseCase): UploadDataViewModel {
            return UploadDataViewModel(
                syncDataUseCase = syncDataUseCase
            )
        }
    }
}