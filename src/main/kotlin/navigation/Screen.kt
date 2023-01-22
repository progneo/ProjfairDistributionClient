package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

data class Screen(
    val sharedScreen: SharedScreen,
    val bundle: Bundle? = null,
)

enum class ScreenRoute {
    UPLOAD,
    ALGORITHM
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

    companion object {
        fun findByRoute(route: ScreenRoute): SharedScreen {
            return when (route) {
                ScreenRoute.UPLOAD -> UploadScreen
                ScreenRoute.ALGORITHM -> AlgorithmScreen
            }
        }
    }
}