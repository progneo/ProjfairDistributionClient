package domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

interface UploadDataRepository {
    val studentsDownloadFlow: MutableStateFlow<Float>
    val projectsDownloadFlow: MutableStateFlow<Float>
    val participationsDownloadFlow: MutableStateFlow<Float>
    val institutesDownloadFlow: MutableStateFlow<Float>
    val departmentsDownloadFlow: MutableStateFlow<Float>
    val supervisorsDownloadFlow: MutableStateFlow<Float>
    suspend fun syncData(): Boolean
    suspend fun uploadExceptionalStudents(file: File): Boolean
}