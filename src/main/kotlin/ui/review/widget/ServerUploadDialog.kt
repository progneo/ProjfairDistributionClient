package ui.review.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.theme.BlueMainDark
import common.theme.BlueMainLight

@Composable
fun ServerUploadDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        modifier = modifier,
        visible = visible,
        mainPart = {
            Column {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Загрузка данных на сервер",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(Modifier.size(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp).align(Alignment.CenterHorizontally),
                    color = BlueMainLight
                )
            }
        },
        buttonsPart = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainDark, contentColor = Color.White)
            ) {
                Text(text = "Закрыть")
            }
        },
        onDismissRequest = onDismissRequest
    )
}