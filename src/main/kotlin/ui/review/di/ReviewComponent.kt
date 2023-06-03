package ui.review.di

import androidx.compose.runtime.Composable
import di.AppComponent
import di.BaseComponent
import navigation.NavController
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.review.screen.ReviewScreen
import ui.review.viewmodel.ReviewViewModel
import javax.inject.Inject

class ReviewComponent(
    appComponent: AppComponent,
    private val navController: NavController
): BaseComponent {

    @Inject
    lateinit var reviewViewModel: ReviewViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {
        ReviewScreen(navController, reviewViewModel)
    }
}