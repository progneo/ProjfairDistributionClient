package data.repository

import domain.repository.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UploadDataRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val studentRepository: StudentRepository,
    private val projectRepository: ProjectRepository,
    private val participationRepository: ParticipationRepository,
    private val instituteRepository: InstituteRepository,
    private val departmentRepository: DepartmentRepository,
    private val supervisorRepository: SupervisorRepository,
    private val loggingRepository: LoggingRepository,
) : UploadDataRepository {

    override val studentsDownloadFlow = studentRepository.downloadFlow
    override val projectsDownloadFlow = projectRepository.downloadFlow
    override val participationsDownloadFlow = participationRepository.downloadFlow
    override val institutesDownloadFlow = instituteRepository.downloadFlow
    override val departmentsDownloadFlow = departmentRepository.downloadFlow
    override val supervisorsDownloadFlow = supervisorRepository.downloadFlow

    val coroutineScope = CoroutineScope(ioDispatcher)

    override suspend fun syncData() {
        coroutineScope.launch {
            studentRepository.syncStudents()
            projectRepository.syncProjects()
            participationRepository.syncParticipations()
            instituteRepository.uploadInstitutes()
            departmentRepository.uploadDepartments()
            supervisorRepository.uploadSupervisors()
        }
    }

    override suspend fun rebaseData() {
        coroutineScope.launch {
            loggingRepository.deleteAll()
            studentRepository.rebaseStudents()
            projectRepository.rebaseProjects()
            participationRepository.rebaseParticipations()
            instituteRepository.uploadInstitutes()
            departmentRepository.uploadDepartments()
            supervisorRepository.uploadSupervisors()
        }
    }

    override fun stopOperations() {
        coroutineScope.coroutineContext.cancelChildren()
        studentRepository.downloadFlow.value = 0f
        projectRepository.downloadFlow.value = 0f
        participationRepository.downloadFlow.value = 0f
        instituteRepository.downloadFlow.value = 0f
        departmentRepository.downloadFlow.value = 0f
        supervisorRepository.downloadFlow.value = 0f
    }
}