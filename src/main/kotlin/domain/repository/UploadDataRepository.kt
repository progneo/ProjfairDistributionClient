package domain.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface UploadDataRepository {
    val studentsDownloadFlow: MutableStateFlow<Float>
    val projectsDownloadFlow: MutableStateFlow<Float>
    val participationsDownloadFlow: MutableStateFlow<Float>
    val institutesDownloadFlow: MutableStateFlow<Float>
    val departmentsDownloadFlow: MutableStateFlow<Float>
    val supervisorsDownloadFlow: MutableStateFlow<Float>
    suspend fun syncData(): Boolean
    suspend fun rebaseData(): Boolean
}