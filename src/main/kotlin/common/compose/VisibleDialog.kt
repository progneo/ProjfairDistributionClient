package common.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VisibleDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(14.dp),
    mainPart: @Composable () -> Unit,
    buttonsPart: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (!visible) return

    AlertDialog(
        modifier = modifier,
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