package data.repository

import domain.repository.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val studentRepository: StudentRepository,
    private val projectRepository: ProjectRepository,
    private val participationRepository: ParticipationRepository,
    private val instituteRepository: InstituteRepository,
    private val departmentRepository: DepartmentRepository,
    private val supervisorRepository: SupervisorRepository,
    private val loggingRepository: LoggingRepository
) : UploadDataRepository {

    override val studentsDownloadFlow = studentRepository.downloadFlow
    override val projectsDownloadFlow = projectRepository.downloadFlow
    override val participationsDownloadFlow = participationRepository.downloadFlow
    override val institutesDownloadFlow = instituteRepository.downloadFlow
    override val departmentsDownloadFlow = departmentRepository.downloadFlow
    override val supervisorsDownloadFlow = supervisorRepository.downloadFlow

    override suspend fun syncData(): Boolean {
        return withContext(ioDispatcher) {
            try {
                studentRepository.syncStudents()
                projectRepository.syncProjects()
                participationRepository.syncParticipations()
                instituteRepository.uploadInstitutes()
                departmentRepository.uploadDepartments()
                supervisorRepository.uploadSupervisors()

                true
            } catch (e: Exception) {
                println(e)
                false
            }
        }
    }

    override suspend fun rebaseData(): Boolean {
        return withContext(ioDispatcher) {
            try {
                loggingRepository.deleteAll()
                studentRepository.rebaseStudents()
                projectRepository.rebaseProjects()
                participationRepository.rebaseParticipations()
                instituteRepository.uploadInstitutes()
                departmentRepository.uploadDepartments()
                supervisorRepository.uploadSupervisors()

                true
            } catch (e: Exception) {
                println(e)
                false
            }
        }
    }
}