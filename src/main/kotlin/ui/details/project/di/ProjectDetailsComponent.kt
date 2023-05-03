package ui.details.project.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import domain.model.Project
import navigation.NavController
import ui.details.project.screen.ProjectDetailsScreen
import ui.preview.viewmodel.PreviewViewModel
import javax.inject.Inject

class ProjectDetailsComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    val project = navController.currentScreen.value.bundle!!.getAny("project") as Project

    @Inject
    lateinit var previewViewModel: PreviewViewModel

    init {
        println("COMPONENT PROJECT INIT")
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        ProjectDetailsScreen(navController, previewViewModel, project)
    }
}