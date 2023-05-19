package ui.distribution_algorithm.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.theme.BlueMainLight

@Composable
fun AlgorithmDialog(
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
                    text = "Работает алгоритм распределения",
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

        },
        onDismissRequest = onDismissRequest
    )
}