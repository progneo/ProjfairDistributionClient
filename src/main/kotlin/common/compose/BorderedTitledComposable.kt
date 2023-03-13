package common.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainDark

@Composable
fun BorderedTitledComposable(
    modifier: Modifier = Modifier,
    title: String = "",
    onClick: (() -> Unit)? = null,
    composableToDisplay: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontSize = 24.sp,
                color = BlueMainDark,
            )
            Spacer(Modifier.size(8.dp))
        }
        Box(
            modifier = Modifier
                .border(
                    BorderStroke(2.dp, BlueMainDark),
                    RoundedCornerShape(10.dp)
                )
                .clickable(
                    enabled = onClick != null
                ) {
                    onClick!!()
                }
        ) {
            composableToDisplay()
        }
    }
}