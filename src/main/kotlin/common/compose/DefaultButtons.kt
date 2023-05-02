package common.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainLight
import navigation.NavController

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
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

@Composable
fun TitledButton(
    modifier: Modifier = Modifier,
    title: String,
    buttonTitle: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.6f),
            text = title,
            color = Color.Black,
            fontSize = 16.sp
        )

        Button(
            enabled = enabled,
            onClick = {
                onClick()
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(width = 100.dp, height = 30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White),
            contentPadding = PaddingValues(1.dp)
        ) {
            Text(
                text = buttonTitle,
                color = Color.White,
            )
        }
    }
}