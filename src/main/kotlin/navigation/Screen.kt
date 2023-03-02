package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class Screen(
    val sharedScreen: SharedScreen,
    val bundle: Bundle? = null,
)

enum class ScreenRoute {
    UPLOAD,
    ALGORITHM,
    PREVIEW,
    PROJECT_DETAILS
}

sealed class SharedScreen(
    val screenRoute: ScreenRoute,
    val parentScreenRoute: ScreenRoute,
    val title: String? = null,
    val icon: ImageVector? = null,
) {
    object UploadScreen : SharedScreen(
        screenRoute = ScreenRoute.UPLOAD,
        parentScreenRoute = ScreenRoute.UPLOAD,
        title = "Загрузить данные",
        icon = Icons.Filled.Add
    )

    object AlgorithmScreen : SharedScreen(
        screenRoute = ScreenRoute.ALGORITHM,
        parentScreenRoute = ScreenRoute.ALGORITHM,
        title = "Алгоритм",
        icon = Icons.Filled.Info
    )

    object PreviewScreen : SharedScreen(
        screenRoute = ScreenRoute.PREVIEW,
        parentScreenRoute = ScreenRoute.PREVIEW,
        title = "Превью",
        icon = Icons.Filled.List
    )

    object ProjectDetailsScreen : SharedScreen(
        screenRoute = ScreenRoute.PROJECT_DETAILS,
        parentScreenRoute = ScreenRoute.PREVIEW,
        title = "Детали проекта",
        icon = Icons.Filled.Settings
    )

    companion object {
        fun findByRoute(route: ScreenRoute): SharedScreen {
            return when (route) {
                ScreenRoute.UPLOAD -> UploadScreen
                ScreenRoute.ALGORITHM -> AlgorithmScreen
                ScreenRoute.PREVIEW -> PreviewScreen
                ScreenRoute.PROJECT_DETAILS -> ProjectDetailsScreen
            }
        }
    }
}