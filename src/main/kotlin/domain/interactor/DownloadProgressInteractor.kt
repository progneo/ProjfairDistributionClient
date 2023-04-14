package domain.interactor

import domain.repository.UploadDataRepository
import javax.inject.Inject

class DownloadProgressInteractor @Inject constructor(
    uploadDataRepository: UploadDataRepository
) {

    val studentsDownloadFlow = uploadDataRepository.studentsDownloadFlow
    val projectsDownloadFlow = uploadDataRepository.projectsDownloadFlow
    val participationsDownloadFlow = uploadDataRepository.participationsDownloadFlow
    val institutesDownloadFlow = uploadDataRepository.institutesDownloadFlow
    val departmentsDownloadFlow = uploadDataRepository.departmentsDownloadFlow
}