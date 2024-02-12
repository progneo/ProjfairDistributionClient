package common.file

import com.grapecity.documents.excel.Workbook
import domain.model.*
import io.realm.kotlin.ext.realmListOf
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun String.unify(): String {
    return this
        .replace("\"", "")
        .dropLastWhile { it == ' ' }
}

object ExportDataToExcel {
    fun writeProjectsWithStudents(
        students: List<Student>,
        notApplied: List<Student>,
        projects: List<Project>,
        participations: List<Participation>,
        institute: Institute,
        isUniformly: Boolean = false,
        filePath: String,
    ) {
        val finalPath =
            if (isUniformly) {
                "$filePath/равномерно_${institute.name.unify()}.xlsx"
            } else {
                "$filePath/${institute.name.unify()}.xlsx"
            }

        val workBook = Workbook()

        var statIndex = 1
        val workSheetStats = workBook.worksheets.get(0)
        workSheetStats.name = "Статистика по проектам"
        workSheetStats.getRange("A$statIndex:F$statIndex").value =
            arrayOf("Название проекта", "ID проекта", "Укомплектованность", "Процент молчунов")

        statIndex++

        for (project in projects) {
            val partsCount = participations.count { it.projectId == project.id }

            workSheetStats.getRange("A$statIndex:F$statIndex").value =
                arrayOf(
                    project.name,
                    project.id,
                    partsCount,
                    if (partsCount == 0) {
                        "0%"
                    } else {
                        "${(participations.count { it.projectId == project.id && it.priority == 5 } / partsCount.toDouble() * 100).toInt()}%"
                    },
                )
            statIndex++
        }

        workBook.worksheets.add()
        var studIndex = 1
        val workSheetStud = workBook.worksheets.get(1)
        workSheetStud.name = "Не зачисленные студенты"
        workSheetStud.getRange("A$studIndex:D$studIndex").value =
            arrayOf("ФИО", "Группа", "Номер зачетной книжки", "Курс")
        studIndex++
        for (student in notApplied) {
            workSheetStud.getRange("A$studIndex:D$studIndex").value =
                arrayOf(
                    student.name,
                    student.group,
                    student.numz,
                    student.course,
                )
            studIndex++
        }

        var index = 2
        for (project in projects) {
            workBook.worksheets.add()
            val workSheet = workBook.worksheets.get(index)

            val tempName =
                project.name
                    .replace("?", "")
                    .replace(":", "")
                    .replace("/", " ")
            workSheet.name = "\"" +
                tempName
                    .substring(0, if (tempName.length < 27) tempName.length else 27) + "\"${index - 1}"

            val projectParticipations = participations.filter { it.projectId == project.id }
            var participationIndexExcel = 1
            workSheet.getRange("A$participationIndexExcel:E$participationIndexExcel").value =
                arrayOf("ID проекта", "Название", "Заказчик", "Руководители", "Группы")
            participationIndexExcel++

            workSheet.getRange("A$participationIndexExcel:E$participationIndexExcel").value =
                arrayOf(
                    project.id,
                    project.name,
                    project.customer,
                    project.supervisors.joinToString { it.name },
                    project.projectSpecialties.map { ps -> ps.specialty?.name }.toSet()
                        .toString().replace("[", "").replace("]", ""),
                )
            participationIndexExcel++

            workSheet.getRange("A$participationIndexExcel:F$participationIndexExcel").value =
                arrayOf("ФИО", "Группа", "Номер зачетной книжки", "Номер приоритета", "Активность", "Курс")
            participationIndexExcel++

            for (p in projectParticipations.sortedBy { it.priority }) {
                val student = students.find { it.id == p.studentId }!!

                workSheet.getRange("A$participationIndexExcel:F$participationIndexExcel").value =
                    arrayOf(
                        student.name,
                        student.group,
                        student.id,
                        p.priority,
                        if (p.priority == 5) {
                            "Молчун"
                        } else if (p.priority == 4) {
                            "Автоматическая заявка"
                        } else {
                            "Активный"
                        },
                        student.course,
                    )
                participationIndexExcel++
            }
            index++
        }

        workBook.save(finalPath)
        deleteExcessLists(finalPath, filePath, index, institute.name.unify(), isUniformly)
    }

    fun writeStudentsByProjects(
        students: List<Student>,
        projects: List<Project>,
        participations: List<Participation>,
        institute: Institute,
        filePath: String,
    ) {
        val finalPath = "$filePath/${institute.name.unify()}.xlsx"

        val workBook = Workbook()

        var statIndex = 1
        val workSheetStats = workBook.worksheets.get(0)
        workSheetStats.name = "Статистика по студентам"
        workSheetStats.getRange("A$statIndex:F$statIndex").value =
            arrayOf(
                "project.id",
                "project.title",
                "supervisor.fio",
                "supervisor.id",
                "candidate.numz",
                "candidate.fio",
                "candidate.name",
            )

        statIndex++

        for (project in projects) {
            val supervisors =
                project.supervisors +
                    if (project.supervisors.isEmpty()) {
                        listOf(
                            Supervisor(
                                id = -1,
                                name = "Нет руководителя",
                                department = Department(id = 0, name = "", institute = Institute(0, "")),
                                roles = realmListOf(),
                            ),
                        )
                    } else {
                        emptyList()
                    }

            val parts = participations.filter { part -> part.projectId == project.id }

            for (supervisor in supervisors) {
                for (part in parts) {
                    val student = students.find { stud -> stud.id == part.studentId }!!

                    workSheetStats.getRange("A$statIndex:G$statIndex").value =
                        arrayOf(
                            project.id,
                            project.name,
                            supervisor.name,
                            supervisor.id,
                            student.numz,
                            student.name,
                            institute.name,
                        )
                    statIndex++
                }
            }
        }

        workBook.save(finalPath)
        deleteExcessLists(finalPath, filePath, 1, institute.name.unify(), false)
    }

    private fun deleteExcessLists(
        filePath: String,
        newFilePath: String,
        lastSheetNumber: Int,
        institute: String,
        isUniformly: Boolean,
    ) {
        val inputStream = FileInputStream(filePath)
        val book = WorkbookFactory.create(inputStream)

        book.removeSheetAt(lastSheetNumber)
        File(newFilePath).mkdir()
        val f =
            if (isUniformly) {
                File("$newFilePath/равномерно_$institute.xlsx")
            } else {
                File("$newFilePath/$institute.xlsx")
            }
        File(filePath).delete()
        f.createNewFile()
        val outputStream = FileOutputStream(f)
        book.write(outputStream)
        outputStream.close()
    }
}
