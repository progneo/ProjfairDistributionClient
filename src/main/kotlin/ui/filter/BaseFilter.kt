package ui.filter

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Text("${FilterType.INSTITUTE.title} : ${instituteFilterConfiguration.filters[FilterType.INSTITUTE]!!.selectedValue.name.toShortInstitute()}")
        Spacer(Modifier.size(16.dp))
        Text("${FilterType.DEPARTMENT.title} : ${instituteFilterConfiguration.filters[FilterType.DEPARTMENT]!!.selectedValue.name}")
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = BlueMainLight, contentColor = Color.White)
        ) {
            Text("Изменить")
        }
    }
}