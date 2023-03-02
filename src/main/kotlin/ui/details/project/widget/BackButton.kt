package ui.details.project.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.theme.BlueMainLight
import navigation.NavController

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 16.dp
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigateBack()
                },
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = BlueMainLight,
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .padding(8.dp)
            )
        }
    }
}