package ru.student.distribution.ui.uploadfiles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.*
import java.awt.FileDialog
import java.io.File

@Composable
fun UploadFileCard(
    fileTypeName: String,
    onPickFileClicked: () -> Unit
) {
    Column {
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

fun openFileDialog(window: ComposeWindow, title: String, allowedExtensions: List<String>, allowMultiSelection: Boolean = true): Set<File> {
    val fileDialog = FileDialog(window, title, FileDialog.LOAD).apply {
        isMultipleMode = allowMultiSelection

        file = allowedExtensions.joinToString(";") { "*$it" }

        isVisible = true
    }

    return fileDialog.files.toSet()
}