package ui.distribution_algorithm.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainLight
import common.theme.GrayLight

@Composable
fun LaunchButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = {
            onClick()
        },
        modifier = modifier
            .clip(shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BlueMainLight,
            contentColor = Color.White,
            disabledBackgroundColor = DarkGray
        )
    ) {
        Text(
            text = "Запустить распределение",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}