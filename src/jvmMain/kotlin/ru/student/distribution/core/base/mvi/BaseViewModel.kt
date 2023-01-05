package ru.student.distribution.core.base.mvi

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel<Intent : UiIntent, State : UiState> {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    protected val coroutineScope = CoroutineScope(Dispatchers.Default)

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()
    val intent = _intent.asSharedFlow()

    init {
        subscribeIntent()
    }

    private fun subscribeIntent() {
        coroutineScope.launch {
            intent.collect {
                handleIntent(it)
            }
        }
    }

    abstract fun handleIntent(intent: Intent)

    fun setIntent(intent: Intent) {
        val newIntent = intent
        coroutineScope.launch { _intent.emit(newIntent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
}