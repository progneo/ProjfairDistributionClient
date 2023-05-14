package ui.preview.widget

import androidx.compose.foundation.*
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
import androidx.compose.ui.unit.dp
import common.compose.VisibleDialog
import common.compose.rememberForeverLazyListState
import common.logging.LoggingEntity
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import common.theme.GrayLight
import domain.model.Log
import domain.model.Project
import domain.model.Student

private const val KEY = "LOGGING"

@Composable
fun LoggingDialog(
    visible: Boolean,
    items: List<Log>,
    students: List<Student>,
    projects: List<Project>,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        visible = visible,
        shape = RoundedCornerShape(40.dp),
        onDismissRequest = onDismissRequest,
        mainPart = {
            val scrollState = rememberForeverLazyListState(KEY)

            Box(
                modifier = Modifier
                    .size(width = 800.dp, height = 600.dp)
                    .border(2.dp, BlueMainDark, RoundedCornerShape(10.dp))
            ) {
                LoggingDialogHeader()
                LazyColumn(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                ) {
                    items(items) { log ->
                        LoggingDialogItem(log)
                        Divider(thickness = 2.dp)
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(
                        scrollState = scrollState
                    ),
                    style = ScrollbarStyle(
                        minimalHeight = LocalScrollbarStyle.current.minimalHeight,
                        thickness = 16.dp,
                        shape = LocalScrollbarStyle.current.shape,
                        hoverDurationMillis = LocalScrollbarStyle.current.hoverDurationMillis,
                        unhoverColor = LocalScrollbarStyle.current.unhoverColor,
                        hoverColor = LocalScrollbarStyle.current.hoverColor
                    )
                )
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
fun LoggingDialogHeader() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
            .fillMaxWidth()
            .background(GrayLight)
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Дата и время",
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
        Text(
            text = "Субъект",
            modifier = Modifier
                .weight(4f)
                .wrapContentWidth()
        )
        Text(
            text = "Действие",
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
        Text(
            text = "Источник",
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
    }
}

@Composable
private fun LoggingDialogItem(
    log: Log
) {
    Row(
        modifier = Modifier
            .padding(4.dp),
        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally)
    ) {
        Text(
            text = log.dateTime,
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
        Text(
            text = mapLogSubjectToString(log.subject!!),
            modifier = Modifier
                .weight(4f)
                .wrapContentWidth()
        )
        Text(
            text = log.type!!.logType.name,
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
        Text(
            text = log.source!!.logSource.name,
            modifier = Modifier
                .weight(2f)
                .wrapContentWidth()
        )
    }
}

private fun mapLogSubjectToString(subject: Any): String {
    return (subject as? LoggingEntity)?.toLog() ?: "Неподдерживаемая сущность"
}

