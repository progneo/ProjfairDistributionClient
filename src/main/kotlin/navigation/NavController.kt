package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import ui.common.BaseGodViewModel

class NavController(
    private val startDestination: Screen,
    private var backStackScreens: MutableSet<Screen> = mutableSetOf()
) {

    var currentScreen: MutableState<Screen> = mutableStateOf(startDestination)

    fun navigate(
        route: ScreenRoute,
        bundle: Bundle? = null,
        baseGodViewModel: BaseGodViewModel? = null,
        addToBackStack: Boolean = true
    ) {
        if (route != currentScreen.value.sharedScreen.screenRoute && addToBackStack) {
            if (backStackScreens.contains(currentScreen.value) && currentScreen.value != startDestination) {
                backStackScreens.remove(currentScreen.value)
            }

            if (route == startDestination.sharedScreen.screenRoute) {
                backStackScreens = mutableSetOf()
            } else {
                backStackScreens.add(currentScreen.value)
            }

            currentScreen.value = Screen(
                SharedScreen.findByRoute(route),
                bundle,
                baseGodViewModel
            )
        } else {
            currentScreen.value = Screen(
                SharedScreen.findByRoute(route),
                bundle,
                baseGodViewModel
            )
        }
        println(backStackScreens.map { it.sharedScreen })
    }

    fun navigateBack() {
        if (backStackScreens.isNotEmpty()) {
            currentScreen.value = backStackScreens.last()
            backStackScreens.remove(currentScreen.value)
        }
    }
}

@Composable
fun rememberNavController(
    startDestination: Screen,
    backStackScreens: MutableSet<Screen> = mutableSetOf()
): MutableState<NavController> = rememberSaveable {
    mutableStateOf(NavController(startDestination, backStackScreens))
}
