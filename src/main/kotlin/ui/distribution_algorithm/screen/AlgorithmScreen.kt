package ui.distribution_algorithm.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.file.ExportDataToExcel
import common.theme.WhiteDark
import navigation.NavController
import ru.student.distribution.domain.distribution.DistributionLauncher
import ru.student.distribution.domain.distribution.DistributionRule
import ui.distribution_algorithm.viewmodel.AlgorithmViewModel
import ui.distribution_algorithm.widget.LaunchButton

@Composable
fun AlgorithmScreen(
    navController: NavController,
    algorithmViewModel: AlgorithmViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize().background(WhiteDark)
    ) {
        LaunchButton(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            val distributionResults = DistributionLauncher(
                students = algorithmViewModel.students.value.toMutableList(),
                projects = algorithmViewModel.projects.value.toMutableList(),
                participations = algorithmViewModel.participations.value.toMutableList(),
                institutes = algorithmViewModel.institutes.value.toMutableList(),
                distributionRule = DistributionRule(
                    maxPlaces = 15,
                    minPlaces = 9
                ),
                specialInstitute = algorithmViewModel.institutes.value.find { it.id == 0 }!!
            ).launch()

            distributionResults.institutesResults.forEach { instituteResults ->
                ExportDataToExcel.writeProjectsWithStudents(
                    students = algorithmViewModel.students.value.toList(),
                    notApplied = instituteResults.notAppliedStudents,
                    projects = instituteResults.projects,
                    participations = instituteResults.participation,
                    institute = instituteResults.institute,
                    isUniformly = true,
                    filePath = "E:/yadmin/"
                )
            }
        }
    }
}