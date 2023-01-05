package ru.student.distribution.domain.repository

interface UploadDataRepository {
    suspend fun syncData(): Boolean
    suspend fun uploadExceptionalStudents(): Boolean
}