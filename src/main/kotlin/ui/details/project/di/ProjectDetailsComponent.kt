package ui.details.project.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import domain.model.Project
import navigation.NavController
import ui.common.BaseGodViewModel
import ui.details.project.screen.ProjectDetailsScreen
import ui.preview.viewmodel.PreviewViewModel
import javax.inject.Inject

class ProjectDetailsComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val project = navController.currentScreen.value.bundle!!.getAny("project") as Project
        val viewModel = navController.currentScreen.value.baseGodViewModel!!
        ProjectDetailsScreen(navController, viewModel, project)
    }
}