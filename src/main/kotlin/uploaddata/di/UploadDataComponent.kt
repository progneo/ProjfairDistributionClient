package uploaddata.di

import androidx.compose.runtime.Composable
import di.AppComponent
import navigation.NavController
import ru.student.distribution.di.BaseComponent
import uploaddata.UploadDataScreen
import uploaddata.UploadDataViewModel
import javax.inject.Inject

//@Scope
//annotation class UploadDataScope
//
//@[UploadDataScope Component(
//    dependencies = [
//        AppComponent::class
//    ],
//    modules = [
//        UploadDataViewModelModule::class
//    ]
//)]
//interface UploadDataComponent {
//
//    @Component.Factory
//    interface Factory {
//
//        fun create(): UploadDataComponent
//    }
//}

//@Module
//interface UploadDataViewModelModule {
//
//    companion object {
//
//        @UploadDataScope
//        @Provides
//        fun provideUploadDataViewModel(syncDataUseCase: SyncDataUseCase): UploadDataViewModel {
//            return UploadDataViewModel(syncDataUseCase)
//        }
//    }
//}

class UploadDataComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

    companion object {
        fun create() {

        }
    }

    @Inject
    lateinit var uploadDataViewModel: UploadDataViewModel

    private val id: Int by lazy {
        val bundle = navController.currentScreen.value.bundle
        bundle?.getInt("id") ?: -1
    }

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        UploadDataScreen(navController, uploadDataViewModel, id)
    }
}