import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dagger.Component
import ru.student.distribution.di.AppComponent
import ru.student.distribution.di.DaggerAppComponent
import ui.uploaddata.di.UploadDataComponent

private val appComponent: AppComponent by lazy {
    DaggerAppComponent
        .factory()
        .create()
}

fun main(args: Array<String>) = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 800.dp, height = 600.dp),
        title = "Medium"
    ) {
//        rememberCoroutineScope().launch {
//            withContext(Dispatchers.IO) {
//                val response = ProjectFairClient.getClient().getProjects()
//                //println(response)
//            }
//        }

//        UploadDataScreen(
//            uploadDataViewModel = UploadDataViewModel(
//                syncDataUseCase = SyncDataUseCase(
//                    uploadDataRepository = UploadDataRepositoryImpl(
//                        ioDispatcher = Dispatchers.IO
//                    )
//                )
//            )
//        )

        UploadDataComponent(appComponent).render()

//        UploadFileCard(
//            fileTypeName = "Students",
//            onPickFileClicked = {
//                openFileDialog(
//                    window = window,
//                    title = "SHIT",
//                    allowedExtensions = listOf(".xlsx", ".xls"),
//                    allowMultiSelection = false
//                )
//            }
//        )
    }
}