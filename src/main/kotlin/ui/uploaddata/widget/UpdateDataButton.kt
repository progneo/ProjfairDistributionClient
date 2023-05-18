package ui.uploaddata.widget


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainLight

@Composable
fun UpdateDataButton(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White),
        contentPadding = contentPadding
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier.padding(8.dp).size(20.dp)
        )
        Text(
           text = title,
            fontSize = 12.sp
        )
    }
}