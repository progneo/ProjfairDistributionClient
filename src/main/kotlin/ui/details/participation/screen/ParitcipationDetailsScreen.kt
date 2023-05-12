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
import ui.common.BaseGodViewModel
import ui.details.participation.widget.ChooseParticipationTable
import ui.details.participation.widget.ParticipationTable
import ui.filter.FilterNode
import ui.filter.FilterType

@Composable
fun ParticipationDetailsScreen(
    navController: NavController,
    project: Project,
    viewModel: BaseGodViewModel,
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
                    text = project.name,
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
        Row {
            ParticipationTable(
                modifier = Modifier.fillMaxWidth(0.475f).padding(24.dp),
                participations = viewModel.getParticipationByProject(project.id),
                viewModel = viewModel
            )
            Column(
                modifier = Modifier.fillMaxWidth(2f/24).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button({}) { Text("name") }
                Button({}) { Text("name") }
            }
            ChooseParticipationTable(
                modifier = Modifier.padding(24.dp),
                filterNode = FilterNode(
                    prev = null,
                    type = FilterType.INSTITUTE,
                    selectedValue = null,
                    next = FilterType.DEPARTMENT
                ),
                viewModel = viewModel
            )
        }
    }
}