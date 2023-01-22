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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainLight

@Composable
fun LaunchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .clip(shape = RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
    ) {
        Text(
            text = "Запустить алгоритм",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}