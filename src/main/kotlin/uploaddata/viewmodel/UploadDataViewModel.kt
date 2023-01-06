package uploaddata.viewmodel

import domain.usecase.base.BaseFlowUseCase
import domain.usecase.uploaddata.UploadExceptionalStudentsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.student.distribution.core.base.DataState
import ru.student.distribution.core.base.mvi.BaseViewModel
import domain.usecase.uploaddata.SyncDataUseCase
import uploaddata.contract.UploadDataContract
import java.io.File
import javax.inject.Inject

class UploadDataViewModel @Inject constructor(
    private val syncDataUseCase: SyncDataUseCase,
    private val uploadExceptionalStudentsUseCase: UploadExceptionalStudentsUseCase,
) : BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    override fun createInitialState(): UploadDataContract.ScreenState {
        return UploadDataContract.ScreenState.Idle
    }

    override fun handleIntent(intent: UploadDataContract.Intent) {
        when (intent) {
            is UploadDataContract.Intent.SyncData -> syncData()
            is UploadDataContract.Intent.UploadExceptionalStudents -> uploadExceptionalStudents(intent.file)
        }
    }

    private fun syncData() {
        handleRequest(syncDataUseCase)
    }

    private fun uploadExceptionalStudents(file: File) {
        handleRequest(uploadExceptionalStudentsUseCase)
    }

    private fun handleRequest(useCase: BaseFlowUseCase<Boolean>) {
        useCase().onEach {
            when (it) {
                is DataState.Error -> {
                    setState {
                        UploadDataContract.ScreenState.SideEffect(
                            UploadDataContract.SideEffect.ShowError(
                                "cannot LOAD data from ${if (useCase is SyncDataUseCase) "server" else "database"}"
                            )
                        )
                    }
                }

                is DataState.Loading -> {
                    setState {
                        UploadDataContract.ScreenState.Data(
                            UploadDataContract.UploadDataState.Loading
                        )
                    }
                }

                is DataState.Success -> {
                    if (it.data) {
                        setState {
                            UploadDataContract.ScreenState.Data(
                                UploadDataContract.UploadDataState.Success
                            )
                        }
                    } else {
                        setState {
                            UploadDataContract.ScreenState.SideEffect(
                                UploadDataContract.SideEffect.ShowError(
                                    "cannot SAVE data to ${if (useCase is SyncDataUseCase) "server" else "database"}"
                                )
                            )
                        }
                    }
                }
            }
        }.launchIn(coroutineScope)
    }
}