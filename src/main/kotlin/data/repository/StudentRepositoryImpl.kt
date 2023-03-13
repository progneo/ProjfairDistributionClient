package data.repository

import data.local.dao.StudentDao
import data.mapper.studentResponseToStudent
import data.remote.api.OrdinaryProjectFairApi
import domain.model.Student
import domain.repository.StudentRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val studentDao: StudentDao,
    private val projectFairApi: OrdinaryProjectFairApi
): StudentRepository {

    override val downloadFlow = MutableStateFlow(0f)

    override fun getStudents(): Flow<ResultsChange<Student>> {
        return studentDao.getAll()
    }

    override suspend fun updateStudent(student: Student) {
        studentDao.update(student)
    }

    override suspend fun insertStudent(student: Student) {
        withContext(ioDispatcher) {
            studentDao.insert(student)
        }
    }

    override suspend fun insertStudent(students: List<Student>) {
        withContext(ioDispatcher) {
            studentDao.insert(students)
        }
    }

    override suspend fun deleteStudent(student: Student) {
        studentDao.delete<Student>(student)
    }

    override suspend fun deleteAllStudents() {
        studentDao.deleteAll<Student>()
    }

    override suspend fun uploadStudents() {
        withContext(ioDispatcher) {
            val students = projectFairApi.getCandidates()
            var current = 0f
            val overall = students.size

            students.forEach {
                insertStudent(studentResponseToStudent(it))
                downloadFlow.value = ++current / overall
            }
        }
    }
}