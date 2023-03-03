package domain.repository

import domain.model.Student
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun getStudents(): Flow<ResultsChange<Student>>
    suspend fun updateStudent(student: Student)
    suspend fun insertStudent(student: Student)
    suspend fun insertStudent(students: List<Student>)
    suspend fun deleteStudent(student: Student)
    suspend fun deleteAllStudents()
    suspend fun uploadStudents()
}