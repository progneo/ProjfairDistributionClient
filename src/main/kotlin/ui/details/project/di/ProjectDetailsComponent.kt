package ui.details.project.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import domain.model.Project
import navigation.NavController
import ui.details.project.screen.ProjectDetailsScreen

class ProjectDetailsComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    val project = navController.currentScreen.value.bundle!!.getAny("project") as Project

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        ProjectDetailsScreen(navController, project)
    }
}