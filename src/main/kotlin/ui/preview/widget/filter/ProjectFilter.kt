package ui.preview.widget.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.compose.ExposedTypedDropdownMenu
import common.compose.rememberExposedMenuStateHolder
import common.mapper.toShortInstitute
import domain.model.Department
import domain.model.Institute
import ui.filter.FilterDialog
import ui.filter.FilterEntity
import ui.filter.FilterType
import ui.preview.viewmodel.PreviewViewModel

@Composable
fun ProjectFilterDialog(
    visible: Boolean,
    instituteFilterConfiguration: InstituteFilterConfiguration,
    previewViewModel: PreviewViewModel,
    onApplyClicked: (InstituteFilterConfiguration) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var isInstituteSelected: Boolean by remember { mutableStateOf(false) }
    var isInstituteReset: Boolean by remember { mutableStateOf(false) }
    var isDepartmentReset: Boolean by remember { mutableStateOf(false) }
    isInstituteSelected =
        instituteFilterConfiguration.selectedInstitute != Institute.Base

    val departments = previewViewModel.filteredDepartments.collectAsState()

    FilterDialog(
        visible = visible,
        filterContent = {
            Column {
                ProjectFilterDropdownItem<Institute>(
                    filterType = FilterType.INSTITUTE,
                    selectedValue = instituteFilterConfiguration.selectedInstitute,
                    items = previewViewModel.institutes.value,
                    isEnabled = true,
                    isReset = isInstituteReset,
                ) { index, _ ->
                    instituteFilterConfiguration.selectedInstitute = previewViewModel.institutes.value[index]

                    previewViewModel.filterDepartments(if (index == 0) null else previewViewModel.institutes.value[index])

                    isInstituteSelected = index != 0
                    isDepartmentReset = true
                    isInstituteReset = false
                }
                Spacer(Modifier.size(32.dp))
                ProjectFilterDropdownItem<Department>(
                    filterType = FilterType.DEPARTMENT,
                    selectedValue = instituteFilterConfiguration.selectedDepartment,
                    items = departments.value,
                    isEnabled = isInstituteSelected,
                    isReset = isDepartmentReset,
                ) { index, _ ->
                    instituteFilterConfiguration.selectedDepartment = previewViewModel.filteredDepartments.value[index]

                    isDepartmentReset = false
                }
            }
        },
        onApplyClicked = {
            if (!isInstituteSelected) {
                instituteFilterConfiguration.selectedDepartment = Department.Base
            }
            onApplyClicked(instituteFilterConfiguration)
            onDismissRequest()
        },
        onResetFilters = {
            instituteFilterConfiguration.selectedInstitute = Institute.Base
            instituteFilterConfiguration.selectedDepartment = Department.Base
            isInstituteSelected = false
            isInstituteReset = true
            isDepartmentReset = true
        },
        onDismissRequest = {
            onDismissRequest()
        }
    )
}

@Composable
private fun <T : FilterEntity> ProjectFilterDropdownItem(
    filterType: FilterType,
    selectedValue: T,
    items: List<T>,
    isEnabled: Boolean,
    isReset: Boolean,
    onClick: (Int, String) -> Unit,
) {
    val stateHolder = rememberExposedMenuStateHolder()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = filterType.title,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(Modifier.size(16.dp))
        ExposedTypedDropdownMenu<T>(
            modifier = Modifier
                .size(width = 400.dp, height = Dp.Unspecified),
            title = selectedValue.name,
            isTitleChangeable = true,
            stateHolder = stateHolder,
            items = items,
            isEnabled = isEnabled,
            isReset = isReset,
            toShortName = String::toShortInstitute
        ) { index, itemClicked ->
            onClick(index, itemClicked)
        }
    }
}

data class InstituteFilterConfiguration(
    var selectedInstitute: Institute,
    var selectedDepartment: Department
)