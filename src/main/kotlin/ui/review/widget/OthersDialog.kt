package ui.review.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.theme.BlueMainDark
import common.theme.BlueMainLight

@Composable
fun OthersDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    VisibleDialog(
        visible = visible,
        shape = RoundedCornerShape(10.dp),
        mainPart = {
            Column(
                modifier = Modifier.size(width = 400.dp, height = 600.dp)
            ) {
                Text(
                    text = "Действия с данными",
                    fontWeight = FontWeight.Bold,
                    color = BlueMainDark,
                    fontSize = 24.sp
                )
                Spacer(Modifier.size(24.dp))
                Text(
                    text = "Выгрузить",
                    color = BlueMainLight,
                    fontSize = 16.sp
                )
                Spacer(Modifier.size(2.dp))
                Divider(thickness = 2.dp, color = BlueMainLight)
                Spacer(Modifier.size(24.dp))
                Text(
                    text = "Загрузить в \"Ярмарку проектов\"",
                    color = BlueMainLight,
                    fontSize = 16.sp
                )
                Spacer(Modifier.size(2.dp))
                Divider(thickness = 2.dp, color = BlueMainLight)
                Spacer(Modifier.size(24.dp))
            }
        },
        buttonsPart = {

        },
        onDismissRequest = onDismissRequest
    )
}