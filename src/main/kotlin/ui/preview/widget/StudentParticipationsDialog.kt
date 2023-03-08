package ui.preview.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.theme.BlueMainLight
import common.theme.GrayLight
import compose.icons.Octicons
import compose.icons.octicons.LinkExternal24
import domain.model.Participation
import ui.preview.viewmodel.PreviewViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun StudentParticipationsDialog(
    visible: Boolean,
    items: List<Participation>,
    previewViewModel: PreviewViewModel,
    onDismissRequest: () -> Unit,
    onProjectLinkClicked: (Int) -> Unit
) {
    if (!visible) return

    AlertDialog(
        shape = RoundedCornerShape(40.dp),
        onDismissRequest = {
            onDismissRequest()
        }, text = {
            Column(
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GrayLight)
                        .padding(4.dp)
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

                LazyColumn(
                    modifier = Modifier.size(300.dp, 150.dp)
                ) {
                    items(items) { studPart ->
                        Row(
                            modifier = Modifier.padding(4.dp),
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
                                    .clickable {
                                        onProjectLinkClicked(studPart.projectId)
                                    }
                            )
                        }
                    }
                }
            }
        },
        buttons = {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean = false,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogStateChange: ((Boolean) -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
) {
    val textPaddingAll = 24.dp
    val buttonPaddingAll = 8.dp
    val dialogShape = RoundedCornerShape(16.dp)

    if (dialogState) {
        AlertDialog(
            onDismissRequest = {
                onDialogStateChange?.invoke(false)
                onDismissRequest?.invoke()
            },
            title = null,
            text = null,
            buttons = {

                Column{
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(Modifier.padding(all = textPaddingAll)){
                        Text(
                            text = "stringResource(R.string.gdprText)"
                        )
                    }
                    Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)

                    Row(
                        modifier = Modifier.padding(all = buttonPaddingAll),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onDialogStateChange?.invoke(false)
                                onDialogPositiveButtonClicked?.invoke()
                            }
                        ) {
                            Text(text = "stringResource(R.string.dialog_ok)", color = MaterialTheme.colors.onSurface)
                        }
                    }
                }

            },
            modifier = modifier,
            shape = dialogShape
        )
    }
}