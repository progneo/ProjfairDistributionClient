package ru.student.distribution.ui.uploaddata

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.theme.BlueMainDark
import common.theme.BlueMainLight

@Composable
fun UploadFileCard(
    fileTypeName: String,
    modifier: Modifier,
    onPickFileClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = fileTypeName,
            fontSize = 16.sp,
            color = BlueMainDark,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )
        Row(
            modifier = Modifier
                .size(width = 300.dp, height = 60.dp)
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = BlueMainLight)
        ) {
            Text(
                text = "No file uploaded",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = "Upload",
                fontSize = 16.sp,
                color = BlueMainDark,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .clickable {
                        onPickFileClicked()
                    }
                    .background(color = Color.White)
                    .wrapContentHeight(align = Alignment.CenterVertically)

            )
        }
    }
}