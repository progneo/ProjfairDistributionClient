package navigation

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import ui.details.participation.di.ParticipationDetailsComponent
import ui.details.project.di.ProjectDetailsComponent
import ui.distribution_algorithm.di.AlgorithmComponent
import ui.preview.di.PreviewComponent
import ui.uploaddata.di.UploadDataComponent

@Composable
fun CustomNavigationHost(
    navController: NavController,
    appComponent: AppComponent,
) {
    val components = mutableMapOf<ScreenRoute, BaseComponent>()

    NavigationHost(navController) {
        composable(SharedScreen.UploadScreen.screenRoute) {
            val component = components.getOrDefault(
                SharedScreen.UploadScreen.screenRoute,
                UploadDataComponent(navController = navController, appComponent = appComponent)
            )
            components[SharedScreen.UploadScreen.screenRoute] = component
            component.render()
        }

        composable(SharedScreen.AlgorithmScreen.screenRoute) {
            val component = components.getOrDefault(
                SharedScreen.AlgorithmScreen.screenRoute,
                AlgorithmComponent(navController = navController, appComponent = appComponent)
            )
            components[SharedScreen.AlgorithmScreen.screenRoute] = component
            component.render()
        }

        composable(SharedScreen.PreviewScreen.screenRoute) {
            val component = components.getOrDefault(
                SharedScreen.PreviewScreen.screenRoute,
                PreviewComponent(navController = navController, appComponent = appComponent)
            )
            components[SharedScreen.PreviewScreen.screenRoute] = component
            println(component)
            component.render()
        }

        composable(SharedScreen.ProjectDetailsScreen.screenRoute) {
            val component = components.getOrDefault(
                SharedScreen.ProjectDetailsScreen.screenRoute,
                ProjectDetailsComponent(navController = navController, appComponent = appComponent)
            )
            components[SharedScreen.ProjectDetailsScreen.screenRoute] = component
            component.render()
        }

        composable(SharedScreen.ParticipationDetailsScreen.screenRoute) {
            val component = components.getOrDefault(
                SharedScreen.ParticipationDetailsScreen.screenRoute,
                ParticipationDetailsComponent(navController = navController, appComponent = appComponent)
            )
            components[SharedScreen.ParticipationDetailsScreen.screenRoute] = component
            component.render()
        }
    }.build()
}