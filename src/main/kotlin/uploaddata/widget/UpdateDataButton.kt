package uploaddata.widget


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theme.BlueMainLight

@Composable
fun UpdateDataButton(
    modifier: Modifier,
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
        Icon(
            imageVector = Icons.Rounded.Refresh,
            contentDescription = "Синхронизировать данные",
            modifier = Modifier.padding(8.dp)
        )
        Text(
           text = "Sync data"
        )
    }
}