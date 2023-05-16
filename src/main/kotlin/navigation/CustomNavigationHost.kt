package navigation

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import ui.details.participation.di.ParticipationDetailsComponent
import ui.details.project.di.ProjectDetailsComponent
import ui.distribution_algorithm.di.AlgorithmComponent
import ui.loading.di.LoadingComponent
import ui.preview.di.PreviewComponent
import ui.review.di.ReviewComponent
import ui.uploaddata.di.UploadDataComponent

@Composable
fun CustomNavigationHost(
    navController: NavController,
    appComponent: AppComponent,
) {
    val components = mutableMapOf<ScreenRoute, BaseComponent>()

    NavigationHost(navController) {
        composable(SharedScreen.UploadScreen.screenRoute) {
            var component = components[SharedScreen.UploadScreen.screenRoute]
            if (component == null) {
                component = UploadDataComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.UploadScreen.screenRoute] = component
            component.render()
        }

        composable(SharedScreen.AlgorithmScreen.screenRoute) {
            var component = components[SharedScreen.AlgorithmScreen.screenRoute]
            if (component == null) {
                component = AlgorithmComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.AlgorithmScreen.screenRoute] = component!!
            component!!.render()
        }

        composable(SharedScreen.PreviewScreen.screenRoute) {
            components.remove(SharedScreen.ProjectDetailsScreen.screenRoute)
            var component = components[SharedScreen.PreviewScreen.screenRoute]
            if (component == null) {
                component = PreviewComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.PreviewScreen.screenRoute] = component!!
            component!!.render()
        }

        composable(SharedScreen.ProjectDetailsScreen.screenRoute) {
            var component = components[SharedScreen.ProjectDetailsScreen.screenRoute]
            if (component == null) {
                component = ProjectDetailsComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.ProjectDetailsScreen.screenRoute] = component!!
            component!!.render()
        }

        composable(SharedScreen.ParticipationDetailsScreen.screenRoute) {
            var component = components[SharedScreen.ParticipationDetailsScreen.screenRoute]
            if (component == null) {
                component = ParticipationDetailsComponent(navController = navController, appComponent = appComponent)
            }
            component!!.render()
        }

        composable(SharedScreen.ReviewScreen.screenRoute) {
            components.remove(SharedScreen.ProjectDetailsScreen.screenRoute)
            var component = components[SharedScreen.ReviewScreen.screenRoute]
            if (component == null) {
                component = ReviewComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.ReviewScreen.screenRoute] = component!!
            component!!.render()
        }

        composable(SharedScreen.LoadingScreen.screenRoute) {
            LoadingComponent().render()
        }
    }.build()
}