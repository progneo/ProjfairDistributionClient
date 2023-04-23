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
    private val departmentRepository: DepartmentRepository,
) : UploadDataRepository {

    override val studentsDownloadFlow = studentRepository.downloadFlow
    override val projectsDownloadFlow = projectRepository.downloadFlow
    override val participationsDownloadFlow = participationRepository.downloadFlow
    override val institutesDownloadFlow = instituteRepository.downloadFlow
    override val departmentsDownloadFlow = departmentRepository.downloadFlow

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            try {
                studentRepository.uploadStudents()
                projectRepository.uploadProjects()
                participationRepository.uploadParticipations()
                instituteRepository.uploadInstitutes()
                departmentRepository.uploadDepartments()

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