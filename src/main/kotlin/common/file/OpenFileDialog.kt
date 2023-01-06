package common.file

import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog
import java.io.File

fun openFileDialog(window: ComposeWindow, title: String, allowedExtensions: List<String>, allowMultiSelection: Boolean = true): File {
    val fileDialog = FileDialog(window, title, FileDialog.LOAD).apply {
        isMultipleMode = allowMultiSelection

        file = allowedExtensions.joinToString(";") { "*$it" }

        isVisible = true
    }

    return fileDialog.files.first()
}