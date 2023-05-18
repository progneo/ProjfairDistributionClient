package base.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.student.distribution.core.base.mvi.UiIntent
import ru.student.distribution.core.base.mvi.UiState

abstract class BaseViewModel {

    protected val coroutineScope = CoroutineScope(Dispatchers.IO)
}