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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.VisibleDialog
import common.mapper.toShortInstitute
import common.theme.BlueMainLight
import ui.preview.widget.InstituteFilterConfiguration

enum class FilterType(val title: String) {
    INSTITUTE("Институт"),
    DEPARTMENT("Кафедра")
}

sealed class FilterSelectedType(val name: String) {
    object All : FilterSelectedType("Все")
    data class Selected(val value: String) : FilterSelectedType(value)
}

const val FILTER_ALL = "Все"

data class FilterValue(
    val values: List<String>,
    var selectedValue: FilterSelectedType,
)

interface FilterConfiguration {
    val filters: MutableMap<FilterType, FilterValue>
    fun copy(): FilterConfiguration
}

@Composable
fun FilterDialog(
    visible: Boolean,
    filterContent: @Composable () -> Unit,
    onApplyClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    VisibleDialog(
        visible = visible,
        textPart = {
            filterContent()
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
    instituteFilterConfiguration: InstituteFilterConfiguration,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterValueText(instituteFilterConfiguration, FilterType.INSTITUTE)
        Spacer(Modifier.size(16.dp))
        FilterValueText(instituteFilterConfiguration, FilterType.DEPARTMENT)
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
        ) {
            Text("Изменить")
        }
    }
}

@Composable
private fun FilterValueText(
    instituteFilterConfiguration: InstituteFilterConfiguration,
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
                append(instituteFilterConfiguration.filters[filterType]!!.selectedValue.name.toShortInstitute())
            }
        }
    )
}
