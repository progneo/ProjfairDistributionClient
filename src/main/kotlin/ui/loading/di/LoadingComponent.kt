package ui.loading.di

import androidx.compose.runtime.Composable
import base.mvi.DataState
import di.AppComponent
import di.BaseComponent
import navigation.NavController
import ui.distribution_algorithm.screen.AlgorithmScreen
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.loading.screen.LoadingScreen
import javax.inject.Inject

class LoadingComponent : BaseComponent {

    @Composable
    override fun render() {
        LoadingScreen()
    }
}