package ui.distribution_algorithm.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.theme.WhiteDark
import domain.model.Participation
import domain.model.Project
import domain.model.Student
import navigation.NavController
import ru.student.distribution.domain.distribution.Distribution
import ui.distribution_algorithm.common.toAlgorithmModel
import ui.distribution_algorithm.widget.LaunchButton

@Composable
fun AlgorithmScreen(
    navController: NavController,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(WhiteDark)
    ) {
        LaunchButton(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            val groups = mapOf("ИСТб" to "ИСТб-1", "АСУб" to "АСУб-1")
            val students = mutableListOf<ru.student.distribution.data.model.Student>()
            (0..35).forEach {
                val groupName = if (it < 15) "АСУб" else "ИСТб"
                val groupNumber = groups[groupName]!!
                students.add(
                    Student(
                        id = it,
                        name = "Name $it",
                        group = groupNumber,
                        shouldDistribute = true,
                        specialtyId = 0
                    ).toAlgorithmModel()
                )
            }
            val projects = mutableListOf<ru.student.distribution.data.model.Project>(
                Project(
                    id = 1,
                    title = "Project 1",
                    places = 15,
                    freePlaces = 15,
                    supervisors = "Supervisor 1",
                    difficulty = 1,
                    description = "",
                    customer = "",
                    goal = "",
                    dateStart = "",
                    dateEnd = "",
                    productResult = "",
                    studyResult = ""
                ).toAlgorithmModel(listOf("ИСТб", "АСУб")),
                Project(
                    id = 2,
                    title = "Project 2",
                    places = 15,
                    freePlaces = 15,
                    supervisors = "Supervisor 2",
                    difficulty = 1,
                    description = "",
                    customer = "",
                    goal = "",
                    dateStart = "",
                    dateEnd = "",
                    productResult = "",
                    studyResult = ""
                ).toAlgorithmModel(listOf("АСУб")),
            )
            val participation = mutableListOf<ru.student.distribution.data.model.Participation>()

            participation.add(
                Participation(
                    id = 0,
                    priority = 1,
                    projectId = 1,
                    studentId = 15
                ).toAlgorithmModel()
            )
            participation.add(
                Participation(
                    id = 1,
                    priority = 1,
                    projectId = 1,
                    studentId = 16
                ).toAlgorithmModel()
            )
            participation.add(
                Participation(
                    id = 2,
                    priority = 2,
                    projectId = 2,
                    studentId = 0
                ).toAlgorithmModel()
            )
            participation.add(
                Participation(
                    id = 3,
                    priority = 1,
                    projectId = 2,
                    studentId = 1
                ).toAlgorithmModel()
            )
            participation.add(
                Participation(
                    id = 4,
                    priority = 1,
                    projectId = 2,
                    studentId = 2
                ).toAlgorithmModel()
            )

            val institute = "Institute"
            val specialities = mutableListOf("ИСТб", "АСУб")
            val specialGroups = mutableListOf<String>()

            Distribution(
                students = students,
                projects = projects,
                participations = participation,
                institute = institute,
                specialties = specialities,
                specialGroups = specialGroups,
                savedPath = "F:/yarmarka_data/output"
            ).executeUniformly()
        }
    }
}