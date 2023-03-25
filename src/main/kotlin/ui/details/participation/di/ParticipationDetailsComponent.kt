package ui.details.participation.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import domain.model.Project
import navigation.NavController
import ui.details.participation.screen.ParticipationDetailsScreen
import ui.preview.viewmodel.PreviewViewModel
import javax.inject.Inject

class ParticipationDetailsComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    @Inject
    lateinit var previewViewModel: PreviewViewModel

    val project = navController.currentScreen.value.bundle!!.getAny("project") as Project

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        ParticipationDetailsScreen(navController, project, previewViewModel)
    }
}