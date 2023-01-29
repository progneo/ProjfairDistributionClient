package data.repository

import data.local.dao.StudentDao
import domain.model.Student
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getStudents(): List<Student> {
        println("GET STUDETNSSFSD")
        return withContext(ioDispatcher) {
            StudentDao.getAll()
        }
    }
}