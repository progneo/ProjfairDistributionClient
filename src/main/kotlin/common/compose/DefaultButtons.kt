package common.compose

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import common.theme.BlueMainLight
import navigation.NavController

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 16.dp
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick()
                },
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BlueMainLight,
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    BaseButton(
        modifier = modifier,
        icon = Icons.Default.ArrowBack,
        onClick = {
            navController.navigateBack()
        }
    )
}