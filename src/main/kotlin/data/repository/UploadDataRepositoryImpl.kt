package ru.student.distribution.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.student.distribution.domain.repository.UploadDataRepository
import javax.inject.Inject

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher
): UploadDataRepository{

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            //throw Exception()
            true
        }
    }

    override suspend fun uploadExceptionalStudents(): Boolean {
        TODO("Not yet implemented")
    }
}