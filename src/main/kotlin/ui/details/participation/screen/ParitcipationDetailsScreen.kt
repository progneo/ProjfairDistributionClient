package ui.details.participation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.BackButton
import common.theme.BlueMainLight
import domain.model.Project
import navigation.NavController
import ui.details.participation.widget.ParticipationTable
import ui.preview.viewmodel.PreviewViewModel

@Composable
fun ParticipationDetailsScreen(
    navController: NavController,
    project: Project,
    previewViewModel: PreviewViewModel,
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                BackButton(
                    modifier = Modifier
                        .padding(16.dp),
                    navController = navController
                )
                Text(
                    text = project.title + project.title,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterEnd).padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White),
            ) {
                Text(
                    text = "Изменить",
                    fontSize = 18.sp
                )
            }
        }
        ParticipationTable(
            modifier = Modifier.padding(24.dp),
            participations = previewViewModel.getParticipationByProject(project.id),
            previewViewModel = previewViewModel
        )
    }
}