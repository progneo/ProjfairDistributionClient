package ui.details.project.widget

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
fun ShowParticipationButton(
    modifier: Modifier = Modifier,
    onShowClicked: () -> Unit,
) {
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp)),
        onClick = {
            onShowClicked()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BlueMainLight,
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Показать заявки",
            fontSize = 36.sp,
        )
    }
}