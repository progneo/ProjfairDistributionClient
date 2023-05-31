package domain.usecase.student

import di.Preview
import di.Review
import domain.model.Student
import domain.repository.StudentRepository
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class GetPreviewStudentsUseCase @Inject constructor(
//    @Preview private val studentRepository: StudentRepository
//)
//
//class GetReviewStudentsUseCase @Inject constructor(
//    @Review private val studentRepository: StudentRepository
//): GetStudentsUseCase(studentRepository)

class GetStudentsUseCase(
    private val studentRepository: StudentRepository
) {
    operator fun invoke(): Flow<ResultsChange<Student>> = studentRepository.getStudents()
}