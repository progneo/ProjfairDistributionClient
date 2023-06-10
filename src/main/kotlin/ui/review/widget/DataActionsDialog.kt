package ui.review.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import common.compose.TitledButton
import common.compose.VisibleDialog
import common.file.ExportDataToExcel
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import domain.model.DistributionResults
import domain.model.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ui.review.viewmodel.ReviewViewModel
import java.io.File

@Composable
fun DataActionsDialog(
    visible: Boolean,
    students: List<Student>,
    reviewViewModel: ReviewViewModel,
    onDismissRequest: () -> Unit,
) {
    val defaultPathText = "Выберите папку для выгрузки"

    val filePathText = remember {
        mutableStateOf<String?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    VisibleDialog(
        visible = visible,
        shape = RoundedCornerShape(10.dp),
        mainPart = {
            var showDirPicker by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.size(width = 400.dp, height = Dp.Unspecified)
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
                        .height(IntrinsicSize.Min)
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
                            .weight(3f)
                    )
                    Text(
                        text = "Выбрать",
                        fontSize = 16.sp,
                        color = BlueMainDark,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(4.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(color = Color.White)
                            .wrapContentHeight(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.size(16.dp))
                TitledButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Списки по институтам",
                    buttonTitle = "Выгрузить",
                    enabled = filePathText.value != null,
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val distributionResults = reviewViewModel.getDistributionResults()
                            val newFilePath = "${filePathText.value!!}/Списки по институтам"
                            File(newFilePath).mkdir()
                            distributionResults.instituteResults.forEach { instituteResult ->
                                if (instituteResult.projects.isEmpty()) return@forEach
                                ExportDataToExcel.writeStudentsByProjects(
                                    students = students,
                                    projects = instituteResult.projects,
                                    participations = reviewViewModel.getAllParticipation(),
                                    institute = instituteResult.institute,
                                    filePath = newFilePath
                                )
                            }
                            File("$newFilePath/ЛИШНИЕ ПРОЕКТЫ.txt").createNewFile()
                            File("$newFilePath/ЛИШНИЕ ПРОЕКТЫ.txt").printWriter().use { out ->
                                distributionResults.excessProjects.forEach { proj ->
                                    out.println(proj)
                                }
                            }
                        }
                    }
                )
                Spacer(Modifier.size(16.dp))
                TitledButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Список всех студентов по проектам",
                    buttonTitle = "Выгрузить",
                    enabled = filePathText.value != null,
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            val distributionResults = reviewViewModel.getDistributionResults()
                            val newFilePath = "${filePathText.value!!}/Списки всех студентов по проектам"
                            File(newFilePath).mkdir()
                            distributionResults.instituteResults.forEach { instituteResult ->
                                if (instituteResult.projects.isEmpty()) return@forEach
                                ExportDataToExcel.writeProjectsWithStudents(
                                    students = students,
                                    notApplied = instituteResult.notAppliedStudents,
                                    projects = instituteResult.projects,
                                    participations = instituteResult.participation,
                                    institute = instituteResult.institute,
                                    filePath = newFilePath
                                )
                            }
                            File("$newFilePath/ЛИШНИЕ ПРОЕКТЫ.txt").createNewFile()
                            File("$newFilePath/ЛИШНИЕ ПРОЕКТЫ.txt").printWriter().use { out ->
                                distributionResults.excessProjects.forEach { proj ->
                                    out.println(proj)
                                }
                            }
                        }
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
                Spacer(Modifier.size(8.dp))
                Button(
                    onClick = {
                        val partLastIndex = reviewViewModel.getParticipationLastIndex()
                        val oldParts = reviewViewModel.getAllParticipation().filter {
                            it.id <= partLastIndex
                        }
                        val newParts = reviewViewModel.getAllParticipation().filter {
                            it.id > partLastIndex
                        }

                        reviewViewModel.updateOnServer(
                            insertParticipation = newParts,
                            updateParticipation = oldParts,
                            updateProjects = reviewViewModel.getAllProjectsFlow().value
                        )
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterHorizontally)
                        .clip(shape = RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
                ) {
                    Text(
                        text = "Загрузить",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
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
            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainDark, contentColor = Color.White)
            ) {
                Text(text = "Закрыть")
            }
        },
        onDismissRequest = onDismissRequest
    )
}