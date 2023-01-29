
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import common.theme.BlueMainLight
import di.AppComponent
import di.DaggerAppComponent
import navigation.CustomNavigationHost
import navigation.Screen
import navigation.SharedScreen
import navigation.rememberNavController

private val appComponent: AppComponent by lazy {
    DaggerAppComponent
        .factory()
        .create()
}

fun main(args: Array<String>) = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 800.dp, height = 600.dp),
        title = "StudentDistributionClient"
    ) {
        App()
    }
}

@Composable
fun App() {
    val navBarScreens = listOf(
        Screen(SharedScreen.UploadScreen),
        Screen(SharedScreen.PreviewScreen),
        Screen(SharedScreen.AlgorithmScreen),
    )
    val navController by rememberNavController(navBarScreens[0])
    val currentScreen by remember {
        navController.currentScreen
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationRail(
                    modifier = Modifier.fillMaxHeight().shadow(2.dp)
                ) {
                    navBarScreens.forEach {
                        NavigationRailItem(
                            selected = currentScreen.sharedScreen.parentScreenRoute == it.sharedScreen.parentScreenRoute,
                            icon = {
                                Icon(
                                    imageVector = it.sharedScreen.icon!!,
                                    contentDescription = it.sharedScreen.title,
                                    tint = if (currentScreen.sharedScreen.parentScreenRoute == it.sharedScreen.parentScreenRoute) BlueMainLight else Color.Gray
                                )
                            },
                            label = {
                                Text(
                                    text = it.sharedScreen.title!!,
                                    color = BlueMainLight,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                )
                            },
                            alwaysShowLabel = false,
                            onClick = {
                                navController.navigate(it.sharedScreen.screenRoute)
                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CustomNavigationHost(navController = navController, appComponent = appComponent)
                }
            }
        }
    }
}