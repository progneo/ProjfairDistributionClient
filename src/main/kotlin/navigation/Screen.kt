package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.EvaIcons
import compose.icons.Octicons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.Download
import compose.icons.evaicons.outline.Cube
import compose.icons.octicons.DesktopDownload24
import ui.common.BaseGodViewModel

data class Screen(
    val sharedScreen: SharedScreen,
    val bundle: Bundle? = null,
    val baseGodViewModel: BaseGodViewModel? = null
)

enum class ScreenRoute {
    UPLOAD,
    ALGORITHM,
    PREVIEW,
    PROJECT_DETAILS,
    PARTICIPATION_DETAILS,
    REVIEW,
    LOADING
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
        title = "Загрузка",
        icon = EvaIcons.Fill.Download
    )

    object AlgorithmScreen : SharedScreen(
        screenRoute = ScreenRoute.ALGORITHM,
        parentScreenRoute = ScreenRoute.ALGORITHM,
        title = "Распределение",
        icon = EvaIcons.Outline.Cube
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

    object ParticipationDetailsScreen : SharedScreen(
        screenRoute = ScreenRoute.PARTICIPATION_DETAILS,
        parentScreenRoute = ScreenRoute.PROJECT_DETAILS,
        title = "Заявки на проект",
        icon = Icons.Filled.Settings
    )

    object ReviewScreen : SharedScreen(
        screenRoute = ScreenRoute.REVIEW,
        parentScreenRoute = ScreenRoute.REVIEW,
        title = "Итоги",
        icon = Icons.Filled.Done
    )

    object LoadingScreen : SharedScreen(
        screenRoute = ScreenRoute.LOADING,
        parentScreenRoute = ScreenRoute.LOADING,
        title = "Загрузка",
        icon = Icons.Filled.Refresh
    )

    companion object {
        fun findByRoute(route: ScreenRoute): SharedScreen {
            return when (route) {
                ScreenRoute.UPLOAD -> UploadScreen
                ScreenRoute.ALGORITHM -> AlgorithmScreen
                ScreenRoute.PREVIEW -> PreviewScreen
                ScreenRoute.PROJECT_DETAILS -> ProjectDetailsScreen
                ScreenRoute.PARTICIPATION_DETAILS -> ParticipationDetailsScreen
                ScreenRoute.REVIEW -> ReviewScreen
                ScreenRoute.LOADING -> LoadingScreen
            }
        }
    }
}