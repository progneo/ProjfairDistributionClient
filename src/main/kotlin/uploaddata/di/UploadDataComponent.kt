package ui.uploaddata.di

import androidx.compose.runtime.Composable
import ru.student.distribution.di.AppComponent
import ru.student.distribution.di.BaseComponent
import ru.student.distribution.ui.uploaddata.UploadDataViewModel
import ru.student.distribution.ui.uploaddata.UploadDataScreen
import javax.inject.Inject

class UploadDataComponent(
    appComponent: AppComponent
): BaseComponent {

    @Inject
    lateinit var uploadDataViewModel: UploadDataViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        UploadDataScreen(uploadDataViewModel)
    }
}