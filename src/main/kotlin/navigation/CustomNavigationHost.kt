package navigation

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import ui.details.participation.di.PreviewParticipationDetailsComponent
import ui.details.participation.di.ReviewParticipationDetailsComponent
import ui.details.project.di.ProjectDetailsComponent
import ui.distribution_algorithm.di.AlgorithmComponent
import ui.loading.di.LoadingComponent
import ui.preview.di.PreviewComponent
import ui.review.di.ReviewComponent
import ui.uploaddata.di.UploadPreviewDataComponent
import ui.uploaddata.di.UploadReviewDataComponent

@Composable
fun CustomNavigationHost(
    navController: NavController,
    appComponent: AppComponent,
) {
    val components = mutableMapOf<ScreenRoute, BaseComponent>()

    NavigationHost(navController) {
        composable(SharedScreen.UploadPreviewScreen.screenRoute) {
            var component = components[SharedScreen.UploadPreviewScreen.screenRoute]
            if (component == null) {
                component = UploadPreviewDataComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.UploadPreviewScreen.screenRoute] = component
            component.render()
        }

        composable(SharedScreen.UploadReviewScreen.screenRoute) {
            var component = components[SharedScreen.UploadReviewScreen.screenRoute]
            if (component == null) {
                component = UploadReviewDataComponent(navController = navController, appComponent = appComponent)
            }
            components[SharedScreen.UploadReviewScreen.screenRoute] = component!!
            component!!.render()
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

        composable(SharedScreen.PreviewParticipationDetailsScreen.screenRoute) {
            var component = components[SharedScreen.PreviewParticipationDetailsScreen.screenRoute]
            if (component == null) {
                component = PreviewParticipationDetailsComponent(navController = navController, appComponent = appComponent)
            }
            component!!.render()
        }

        composable(SharedScreen.ReviewParticipationDetailsScreen.screenRoute) {
            var component = components[SharedScreen.ReviewParticipationDetailsScreen.screenRoute]
            if (component == null) {
                component = ReviewParticipationDetailsComponent(navController = navController, appComponent = appComponent)
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