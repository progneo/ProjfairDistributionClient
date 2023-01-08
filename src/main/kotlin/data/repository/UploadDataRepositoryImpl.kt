package data.repository

import data.local.dao.StudentDao
import domain.repository.UploadDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import parsing.excel.student.ExceptionalStudentExcelReader
import java.io.File
import javax.inject.Inject

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher
): UploadDataRepository {

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            true
        }
    }

    override suspend fun uploadExceptionalStudents(file: File): Boolean {
        return withContext(ioDispatcher) {
            try {
                val studentIds = ExceptionalStudentExcelReader().read(file.path)
                StudentDao.markStudentsAsExceptional(studentIds)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}