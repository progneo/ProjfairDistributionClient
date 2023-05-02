package ui.review.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import common.compose.TitledButton
import common.compose.VisibleDialog
import common.theme.BlueMainDark
import common.theme.BlueMainLight

@Composable
fun DataActionsDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    val defaultPathText = "Выберите папку для выгрузки"

    val filePathText = remember {
        mutableStateOf<String?>(null)
    }

    VisibleDialog(
        visible = visible,
        shape = RoundedCornerShape(10.dp),
        mainPart = {
            var showDirPicker by remember { mutableStateOf(false) }

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
                Spacer(Modifier.size(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .defaultMinSize(minHeight = 50.dp)
                        .clip(shape = RoundedCornerShape(14.dp))
                        .background(color = BlueMainLight)
                        .clickable {
                            showDirPicker = true
                        }
                ) {
                    Text(
                        text = filePathText.value ?: defaultPathText,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth(0.7f)
                    )
                    Text(
                        text = "Выбрать",
                        fontSize = 16.sp,
                        color = BlueMainDark,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                            .padding(4.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(color = Color.White)
                            //.height(IntrinsicSize.Min)
                            //.wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Spacer(Modifier.size(16.dp))
                TitledButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Списки по институтам",
                    buttonTitle = "Выгрузить",
                    enabled = filePathText.value != null,
                    onClick = {

                    }
                )
                Spacer(Modifier.size(16.dp))
                TitledButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Список всех студентов по проектам",
                    buttonTitle = "Выгрузить",
                    enabled = filePathText.value != null,
                    onClick = {

                    }
                )
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

            DirectoryPicker(
                show = showDirPicker,
                initialDirectory = filePathText.value
            ) { path ->
                showDirPicker = false
                if (path != null) {
                    filePathText.value = path
                }
            }
        },
        buttonsPart = {

        },
        onDismissRequest = onDismissRequest
    )
}