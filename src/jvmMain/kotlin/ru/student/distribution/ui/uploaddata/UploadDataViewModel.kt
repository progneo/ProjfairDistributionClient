package ru.student.distribution.ui.uploaddata

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.student.distribution.core.base.DataState
import ru.student.distribution.core.base.mvi.BaseViewModel
import ru.student.distribution.domain.usecase.uploaddata.SyncDataUseCase

class UploadDataViewModel(
    private val syncDataUseCase: SyncDataUseCase
): BaseViewModel<UploadDataContract.Intent, UploadDataContract.ScreenState>() {

    override fun createInitialState(): UploadDataContract.ScreenState {
        return UploadDataContract.ScreenState.Idle
    }

    override fun handleIntent(intent: UploadDataContract.Intent) {
        when (intent) {
            is UploadDataContract.Intent.SyncData -> syncData()
            is UploadDataContract.Intent.UploadExceptionalStudents -> uploadExceptionalStudents()
        }
    }

    private fun syncData() {
        println("SYNC DATA")
        syncDataUseCase().onEach {
            when (it) {
                is DataState.Error -> {
                    setState {
                        UploadDataContract.ScreenState.SideEffect(
                            UploadDataContract.SideEffect.ShowError("cant LOAD data from server")
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
                                UploadDataContract.SideEffect.ShowError("cant SAVE data from server")
                            )
                        }
                    }
                }
            }
        }.launchIn(coroutineScope)
    }

    private fun uploadExceptionalStudents() {

    }
}