package ui.preview.widget

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.theme.*
import domain.model.*
import ui.details.project.widget.EditableSearchField

private const val KEY = "LOGGING"

@Composable
fun SupervisorSearchDialog(
    visible: Boolean,
    supervisors: List<Supervisor>,
    searchString: String,
    onDataChanges: (String) -> Unit,
    onSupervisorClicked: (Supervisor) -> Unit,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        modifier = Modifier.size(width = 1280.dp, height = 800.dp),
        visible = visible,
        shape = RoundedCornerShape(40.dp),
        onDismissRequest = onDismissRequest,
        mainPart = {
            val scrollState = rememberLazyListState()

            Column(
                modifier = Modifier
                    .size(width = 1280.dp, height = 800.dp)
            ) {
                Spacer(Modifier.size(8.dp))
                EditableSearchField(
                    modifier = Modifier.size(width = 300.dp, height = Dp.Unspecified),
                    text = searchString,
                    content = "Поиск",
                    onDataChanged = { searchString ->
                        onDataChanges(searchString)
                    }
                )
                Spacer(Modifier.size(16.dp))
                Box(
                    modifier = Modifier
                        .border(2.dp, BlueMainDark, RoundedCornerShape(10.dp))
                ) {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    ) {
                        items(supervisors) { supervisor ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSupervisorClicked(supervisor)
                                    }
                                    .padding(6.dp),
                                text = supervisor.name,
                                fontSize = 16.sp
                            )
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
            }
        },
        buttonsPart = {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlueMainLight,
                    contentColor = Color.White
                )
            ) {
                Text("Закрыть")
            }
        }
    )
}