package data.repository

import domain.repository.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import parsing.excel.student.ExceptionalStudentExcelReader
import java.io.File
import javax.inject.Inject

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val studentRepository: StudentRepository,
    private val projectRepository: ProjectRepository,
    private val participationRepository: ParticipationRepository,
    private val instituteRepository: InstituteRepository,
): UploadDataRepository {

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            try {
                studentRepository.uploadStudents()
                projectRepository.uploadProjects()
                participationRepository.uploadParticipations()
                instituteRepository.uploadInstitutes()

                true
            } catch (e: Exception) {
                println(e)
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