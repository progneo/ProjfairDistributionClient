package ui.details.project.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import common.compose.*
import common.mapper.toShortName
import domain.model.Project
import domain.model.ProjectSpecialty
import io.realm.kotlin.ext.realmListOf
import navigation.Bundle
import navigation.NavController
import navigation.ScreenRoute
import ui.details.project.widget.*
import ui.preview.viewmodel.PreviewViewModel

@Composable
fun ProjectDetailsScreen(
    navController: NavController,
    previewViewModel: PreviewViewModel,
    project: Project,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        val supervisorStateHolder = rememberExposedMenuStateHolder()
        val distributeSpecialtyStateHolder = rememberExposedMenuStateHolder()
        val participationSpecialtyStateHolder = rememberExposedMenuStateHolder()

        val supervisors = rememberSaveable {
            mutableStateListOf(*project.supervisors.toTypedArray())
        }
        val supervisorDropDownItems = rememberSaveable {
            mutableStateOf(previewViewModel.getFilteredSupervisors(project.department!!))
        }

        val distributeSpecialties = rememberSaveable {
            mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 1
            }.toTypedArray())
        }

        val participationSpecialties = rememberSaveable {
            mutableStateListOf<ProjectSpecialty>(*project.projectSpecialties.filter { ps ->
                ps.priority == null || ps.priority == 2
            }.toTypedArray())
        }

        //new project parameters
        var title = project.name
        var updatedSupervisors = project.supervisors.toList()
        var goal = project.goal
        var customer = project.customer
        var description = project.description
        var productResult = project.productResult
        var studyResult = project.studyResult
        var updatedDistributeSpecialties = project.projectSpecialties.filter { ps ->
            ps.priority == null || ps.priority == 1
        }
        var updatedParticipationSpecialties = project.projectSpecialties.filter { ps ->
            ps.priority == null || ps.priority == 2
        }

        Row(modifier = Modifier.padding(16.dp)) {
            BackButton(navController = navController)
            Spacer(modifier = Modifier.size(16.dp))
            TitleField(title = project.name) {
                title = it
            }
        }
        ExposedTypedDropdownMenuWithChips(
            modifier = Modifier.width(300.dp),
            title = "Преподаватель",
            isTitleChangeable = false,
            stateHolder = supervisorStateHolder,
            itemsState = supervisors,
            dropdownItems = supervisorDropDownItems.value,
            toShortName = String::toShortName
        ) {
            updatedSupervisors = it
        }
        EditableDescriptionField(title = "Цель проекта", content = project.goal ?: "") {
            goal = it
        }
        BorderedRadioButtonGroupColumn(
            titles = listOf(
                Title("Легко"), Title("Средне"), Title("Сложно")
            ), selected = project.difficulty - 1, title = "Сложность"
        )
        EditableDescriptionField(title = "Заказчик", content = project.customer ?: "") {
            customer = it
        }
        EditableDescriptionField(title = "Описание", content = project.description ?: "") {
            description = it
        }
        EditableDescriptionField(title = "Ожидаемый продуктовый результат", content = project.productResult) {
            productResult = it
        }
        EditableDescriptionField(title = "Ожидаемый учебный результат", content = project.studyResult) {
            studyResult = it
        }
        SpecialtyPicker(modifier = Modifier.width(200.dp),
            title = "Специальности для молчунов",
            itemsState = distributeSpecialties,
            stateHolder = distributeSpecialtyStateHolder,
            dropdownItems = previewViewModel.specialties.value,
            priority = 1,
            onDataChange = {
                updatedDistributeSpecialties = it
            })
        SpecialtyPicker(modifier = Modifier.width(200.dp),
            title = "Специальности для заявок",
            itemsState = participationSpecialties,
            stateHolder = participationSpecialtyStateHolder,
            dropdownItems = previewViewModel.specialties.value,
            priority = 2,
            onDataChange = {
                updatedParticipationSpecialties = it
            })
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            SaveButton {
                val newProject = Project(
                    id = project.id,
                    name = title,
                    places = project.places, //TODO: maybe update places somehow
                    freePlaces = project.freePlaces,
                    goal = goal,
                    difficulty = project.difficulty,
                    description = description,
                    dateStart = project.dateStart,
                    dateEnd = project.dateEnd,
                    customer = customer,
                    productResult = productResult,
                    studyResult = studyResult,
                    department = project.department,
                    supervisors = realmListOf(*updatedSupervisors.toTypedArray()),
                    projectSpecialties = realmListOf(*(updatedDistributeSpecialties + updatedParticipationSpecialties).toTypedArray())
                )

                newProject.projectSpecialties.forEach(::println)

                previewViewModel.updateProject(newProject)
            }
            ShowParticipationButton {
                val bundle = Bundle()
                bundle.put("project", project)
                println(bundle)
                navController.navigate(ScreenRoute.PARTICIPATION_DETAILS, bundle)
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}

data class SelectedIndexes(
    val map: MutableMap<Int, List<Boolean>>,
)