package ui.uploaddata.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import di.Review
import navigation.NavController
import ui.uploaddata.screen.UploadDataScreen
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class UploadReviewDataComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

    @Review
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