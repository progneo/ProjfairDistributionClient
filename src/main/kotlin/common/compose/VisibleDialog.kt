package common.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VisibleDialog(
    visible: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    mainPart: @Composable () -> Unit,
    buttonsPart: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (!visible) return

    AlertDialog(
        shape = shape,
        onDismissRequest = {
            //onDismissRequest()
        }, text = {
            mainPart()
        },
        buttons = {
            buttonsPart()
        }
    )
}