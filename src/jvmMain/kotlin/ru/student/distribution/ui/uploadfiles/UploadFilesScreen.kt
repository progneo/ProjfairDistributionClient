package ru.student.distribution.ui.uploadfiles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun UploadFilesScreen(

) {
    Box {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            UploadFileCard("Students", {})
            UploadFileCard("Teachers", {})
            UpdateFilesButton()
        }
    }
}