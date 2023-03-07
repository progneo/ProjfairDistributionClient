package ui.distribution_algorithm.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.theme.WhiteDark
import navigation.NavController
import ui.distribution_algorithm.widget.LaunchButton

@Composable
fun AlgorithmScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(WhiteDark)
    ) {
        LaunchButton(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

        }
    }
}