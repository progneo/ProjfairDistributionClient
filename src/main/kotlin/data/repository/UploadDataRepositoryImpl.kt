package data.repository

import domain.model.Participation
import domain.model.Project
import domain.model.Specialty
import domain.model.Student
import domain.repository.StudentRepository
import domain.repository.UploadDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import parsing.excel.student.ExceptionalStudentExcelReader
import java.io.File
import javax.inject.Inject

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val studentRepository: StudentRepository
): UploadDataRepository {

    var id = 1000

    override suspend fun insertStudent() {
        withContext(Dispatchers.IO) {
            println("INSERTING")
            studentRepository.insertStudent(
                Student(
                    id = id++,
                    name = "new name $id",
                    group = "asdf",
                    shouldDistribute = true,
                    specialtyId = 0
                )
            )
        }
    }

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            val groups = mapOf(0 to "ИСТб-1", 1 to "АСУб-1")
            val students = mutableListOf<Student>()
            (0..35).forEach {
                val groupId = if (it < 15) 1 else 0
                val groupNumber = groups[groupId]!!
                students.add(
                    Student(
                        id = it,
                        name = "Name $it",
                        group = groupNumber,
                        shouldDistribute = true,
                        specialtyId = groupId
                    )
                )
            }
            studentRepository.insertStudent(students)
            val projects = mutableListOf<Project>(
                Project(
                    id = 1,
                    title = "Project 1",
                    places = 15,
                    freePlaces = 15,
                    supervisors = "Supervisor 1",
                    difficulty = 1,
                    customer = "",
                    goal = "",
                    dateStart = "",
                    dateEnd = "",
                    productResult = "",
                    studyResult = ""
                ),
                Project(
                    id = 2,
                    title = "Project 2",
                    places = 15,
                    freePlaces = 15,
                    supervisors = "Supervisor 2",
                    difficulty = 1,
                    customer = "",
                    goal = "",
                    dateStart = "",
                    dateEnd = "",
                    productResult = "",
                    studyResult = ""
                ),
            )
            val participation = mutableListOf<Participation>()

            participation.add(
                Participation(
                    id = 0,
                    priority = 1,
                    projectId = 1,
                    studentId = 15
                )
            )
            participation.add(
                Participation(
                    id = 1,
                    priority = 1,
                    projectId = 1,
                    studentId = 16
                )
            )
            participation.add(
                Participation(
                    id = 2,
                    priority = 2,
                    projectId = 2,
                    studentId = 0
                )
            )
            participation.add(
                Participation(
                    id = 3,
                    priority = 1,
                    projectId = 2,
                    studentId = 1
                )
            )
            participation.add(
                Participation(
                    id = 4,
                    priority = 1,
                    projectId = 2,
                    studentId = 2
                )
            )

            val institute = "Institute"
            val specialities = mutableListOf(
                Specialty(id = 0, name = "ИСТб"),
                Specialty(id = 1, name = "АСУб")
            )
            val specialGroups = mutableListOf<String>()

            try {
//                SpecialtyDao.insert(specialities)
//                StudentDao.insert(students)
//                ProjectDao.insert(projects)
//                ParticipationDao.insert(participation)
//
//                println(SpecialtyDao.getAll())
//                println(StudentDao.getAll())
//                println(ProjectDao.getAll())
//                println(ParticipationDao.getAll())

                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun uploadExceptionalStudents(file: File): Boolean {
        return withContext(ioDispatcher) {
            try {
                val studentIds = ExceptionalStudentExcelReader().read(file.path)
                //StudentDao.markStudentsAsExceptional(studentIds)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}