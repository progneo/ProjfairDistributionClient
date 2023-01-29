package ui.preview.di

import androidx.compose.runtime.Composable
import di.AppComponent
import navigation.NavController
import ru.student.distribution.di.BaseComponent
import ui.preview.screen.PreviewScreen
import ui.preview.viewmodel.PreviewViewModel
import javax.inject.Inject

class PreviewComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    @Inject
    lateinit var previewViewModel: PreviewViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        PreviewScreen(navController, previewViewModel)
    }
}