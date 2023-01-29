package ui.distribution_algorithm.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import navigation.NavController
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