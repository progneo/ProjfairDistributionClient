package ui.uploaddata.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import navigation.NavController
import ui.uploaddata.screen.UploadDataScreen
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class UploadDataComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

    @Inject
    lateinit var uploadDataViewModel: UploadDataViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        UploadDataScreen(navController, uploadDataViewModel)
    }
}