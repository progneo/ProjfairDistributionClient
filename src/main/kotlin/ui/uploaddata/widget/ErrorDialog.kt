package ui.uploaddata.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.compose.VisibleDialog
import common.theme.BlueMainLight

@Composable
fun ErrorDialog(
    visible: Boolean,
    errorText: String,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        visible = visible,
        mainPart = {
            Column(
                modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified).fillMaxWidth().wrapContentHeight()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = errorText
                )
            }
        },
        buttonsPart = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Закрыть"
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}