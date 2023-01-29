package ui.uploaddata.di

import androidx.compose.runtime.Composable
import di.AppComponent
import navigation.NavController
import ru.student.distribution.di.BaseComponent
import ui.uploaddata.screen.UploadDataScreen
import ui.uploaddata.viewmodel.UploadDataViewModel
import javax.inject.Inject

class UploadDataComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

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