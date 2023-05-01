package ui.filter

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.mapper.toShortInstitute
import common.theme.BlueMainDark
import common.theme.BlueMainLight
import ui.preview.widget.filter.InstituteFilterConfiguration

@Composable
fun FilterDialog(
    visible: Boolean,
    filterContent: @Composable () -> Unit,
    onApplyClicked: () -> Unit,
    onResetFilters: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        visible = visible,
        mainPart = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Фильтры", color = BlueMainLight, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = {
                            onResetFilters()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainDark, contentColor = Color.White)
                    ) {
                        Text("Сбросить")
                    }
                }
                Spacer(Modifier.size(16.dp))
                filterContent()
            }
        },
        buttonsPart = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onApplyClicked()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
                ) {
                    Text("Применить")
                }
                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
                ) {
                    Text("Закрыть")
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}

@Composable
fun FilterConfigurationBlock(
    modifier: Modifier = Modifier,
    instituteFilterConfiguration: InstituteFilterConfiguration,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterValueText(instituteFilterConfiguration.selectedInstitute.name, FilterType.INSTITUTE)
        Spacer(Modifier.size(16.dp))
        FilterValueText(instituteFilterConfiguration.selectedDepartment.name, FilterType.DEPARTMENT)
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
        ) {
            Text(
                text = "Изменить",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun FilterValueText(
    text: String,
    filterType: FilterType,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontSize = 22.sp
                )
            ) {
                append(filterType.title)
            }
            append(" : ")
            withStyle(
                style = SpanStyle(
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            ) {
                when (filterType) {
                    FilterType.INSTITUTE -> {
                        append(text.toShortInstitute())
                    }
                    FilterType.DEPARTMENT -> {
                        append(text.toShortInstitute())
                    }
                    else -> {
                        append(text.toShortInstitute())
                    }
                }
            }
        }
    )
}
