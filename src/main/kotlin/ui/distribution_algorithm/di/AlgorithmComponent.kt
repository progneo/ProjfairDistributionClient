package ui.distribution_algorithm.di

import androidx.compose.runtime.Composable
import di.AppComponent
import navigation.NavController
import ru.student.distribution.di.BaseComponent
import ui.distribution_algorithm.screen.AlgorithmScreen

class AlgorithmComponent(
    appComponent: AppComponent,
    private val navController: NavController
) : BaseComponent {

//    init {
//        appComponent.inject(this)
//    }

    @Composable
    override fun render() {
        AlgorithmScreen(navController)
    }
}