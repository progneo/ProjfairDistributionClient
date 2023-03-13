package ui.preview.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import common.theme.GrayLight
import compose.icons.Octicons
import compose.icons.octicons.LinkExternal24
import domain.model.Participation

@Composable
fun StudentParticipationsDialog(
    visible: Boolean,
    title: String,
    subtitle: String,
    items: List<Participation>,
    onDismissRequest: () -> Unit,
    onProjectLinkClicked: (Int) -> Unit
) {
    VisibleDialog(
        visible = visible,
        shape = RoundedCornerShape(40.dp),
        onDismissRequest = {
            onDismissRequest()
        }, textPart = {
            Column {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = BlueMainDark,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = BlueMainLight
                )
                Spacer(Modifier.size(8.dp))
                Column(
                    modifier = Modifier
                        .border(2.dp, BlueMainDark, RoundedCornerShape(10.dp))
                ) {
                    StudentParticipationDialogHeader()
                    LazyColumn(
                        modifier = Modifier
                            .size(300.dp, 150.dp)
                            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    ) {
                        items(items) { studPart ->
                            StudentParticipationDialogItem(studPart, onProjectLinkClicked)
                            Divider(thickness = 2.dp)
                        }
                    }
                }
            }
        },
        buttonsPart = {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueMainLight,
                    contentColor = Color.White
                )
            ) {
                Text("Закрыть")
            }
        }
    )
}

@Composable
private fun StudentParticipationDialogHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrayLight)
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Text(
            text = "ID проекта",
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .wrapContentWidth()
        )
        Text(
            text = "Приоритет",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth()
        )
        Text(
            text = "Ссылка",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
    }
    Divider(thickness = 2.dp)
}

@Composable
private fun StudentParticipationDialogItem(
    studPart: Participation,
    onProjectLinkClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onProjectLinkClicked(studPart.projectId)
            }
            .padding(4.dp),
        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)
    ) {
        Text(
            text = studPart.projectId.toString(),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .wrapContentWidth()
        )
        Text(
            text = studPart.priority.toString(),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .wrapContentWidth()
        )
        Icon(
            imageVector = Octicons.LinkExternal24,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(20.dp)
                .wrapContentWidth()
        )
    }
}