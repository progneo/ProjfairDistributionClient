package ui.uploaddata.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import di.Preview
import navigation.NavController
import ui.uploaddata.screen.UploadDataScreen
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class UploadPreviewDataComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

    @Preview
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