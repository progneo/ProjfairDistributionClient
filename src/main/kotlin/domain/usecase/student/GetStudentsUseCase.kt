package domain.usecase.student

import data.repository.StudentRepositoryImpl
import domain.model.Student
import domain.repository.UploadDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentRepositoryImpl: StudentRepositoryImpl
) {

    operator fun invoke(): Flow<List<Student>> = flow {
        emit(studentRepositoryImpl.getStudents())
    }
}

class InsertStudentUseCase @Inject constructor(
    private val uploadDataRepository: UploadDataRepository
) {

    suspend operator fun invoke() {
        uploadDataRepository.insertStudent()
    }
}