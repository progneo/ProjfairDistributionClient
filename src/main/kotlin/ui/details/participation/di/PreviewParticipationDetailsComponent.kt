package ui.details.participation.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import di.Preview
import domain.model.Project
import navigation.NavController
import ui.details.participation.screen.ParticipationDetailsScreen
import ui.details.participation.viewmodel.ParticipationDetailsViewModel
import javax.inject.Inject

class PreviewParticipationDetailsComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    @Preview
    @Inject
    lateinit var participationDetailsViewModel: ParticipationDetailsViewModel

    val project = navController.currentScreen.value.bundle!!.getAny("project") as Project

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        val viewModel = navController.currentScreen.value.baseGodViewModel!!
        ParticipationDetailsScreen(navController, project, viewModel, participationDetailsViewModel)
    }
}