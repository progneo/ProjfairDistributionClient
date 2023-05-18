package ui.uploaddata.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import common.compose.VisibleDialog
import common.theme.BlueMainLight
import common.theme.BlueMainLight25
import ui.uploaddata.viewmodel.DataActionType
import ui.uploaddata.viewmodel.DownloadType

@Composable
fun DownloadProgressDialog(
    visible: Boolean,
    progressBars: Map<DownloadType, Float>,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        visible = visible,
        mainPart = {
            Column(
                modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified).fillMaxWidth().wrapContentHeight()
            ) {
                progressBars.forEach { progressItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = progressItem.key.title,
                            modifier = Modifier.fillMaxWidth(0.3f)
                        )
                        Spacer(Modifier.size(width = 8.dp, height = 1.dp))
                        DownloadProgressBar(
                            progress = progressItem.value,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Spacer(Modifier.size(width = 8.dp, height = 1.dp))
                        Text(
                            text = "${String.format("%.2f", progressItem.value * 100)}%",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                }
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

@Composable
private fun DownloadProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    LinearProgressIndicator(
        modifier = modifier,
        progress = progress,
        color = BlueMainLight,
        backgroundColor = BlueMainLight25
    )
}