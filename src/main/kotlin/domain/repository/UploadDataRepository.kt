package domain.repository

import java.io.File

interface UploadDataRepository {
    suspend fun syncData(): Boolean
    suspend fun uploadExceptionalStudents(file: File): Boolean
    suspend fun insertStudent()
}