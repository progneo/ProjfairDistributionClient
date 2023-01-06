package navigation

import androidx.compose.runtime.Composable
import di.AppComponent
import uploaddata.di.UploadDataComponent

@Composable
fun CustomNavigationHost(
    navController: NavController,
    appComponent: AppComponent
) {

    NavigationHost(navController) {
        composable(SharedScreen.UploadScreen.screenRoute) {
            UploadDataComponent(navController = navController, appComponent = appComponent).render()
        }

        composable(SharedScreen.InfoScreen.screenRoute) {
            UploadDataComponent(navController = navController, appComponent = appComponent).render()
        }
    }.build()
}