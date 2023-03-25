package ui.details.project.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.compose.*
import common.mapper.toShortName
import domain.model.Project
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import ui.details.project.widget.EditableDescriptionField
import ui.details.project.widget.SaveButton
import ui.details.project.widget.ShowParticipationButton
import ui.details.project.widget.TitleField

@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    project: Project,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .verticalScroll(ScrollState(0))
    ) {
        val stateHolder = rememberExposedMenuStateHolder()
        val supervisors = remember {
            mutableStateListOf("Аршинский Вадим Леонидович", "Серышева Ирина Анатольевна", "Лукаш Олег")
        }
        val dropdownItems =
            mutableListOf<String>("Аршинский Вадим Леонидович", "Серышева Ирина Анатольевна", "Лукаш Олег")

        Row(modifier = Modifier.padding(16.dp)) {
            BackButton(navController = navController)
            Spacer(modifier = Modifier.size(16.dp))
            TitleField(title = project.title)
        }
        ExposedDropdownMenuWithChips(
            modifier = Modifier.width(300.dp),
            title = "Преподаватель",
            isTitleChangeable = false,
            stateHolder = stateHolder,
            itemsState = supervisors,
            dropdownItems = dropdownItems,
            toShortName = String::toShortName
        )
        EditableDescriptionField(title = "Цель проекта", content = project.goal ?: "")
        BorderedRadioButtonGroupColumn(
            titles = listOf(
                Title("Легко"),
                Title("Средне"),
                Title("Сложно")
            ),
            selected = project.difficulty - 1,
            title = "Сложность"
        )
        EditableDescriptionField(title = "Заказчик", content = project.customer ?: "")
        EditableDescriptionField(title = "Описание", content = project.description ?: "")
        EditableDescriptionField(title = "Ожидаемый продуктовый результат", content = project.productResult)
        EditableDescriptionField(title = "Ожидаемый учебный результат", content = project.studyResult)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            SaveButton {
                //TODO: implement saving
            }
            ShowParticipationButton {
                val bundle = Bundle()
                bundle.put("project", project)
                navController.navigate(ScreenRoute.PARTICIPATION_DETAILS, bundle)
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}