package com.kafka.ui_common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KProperty1

abstract class ReduxViewModel<S>(
    initialState: S
) : ViewModel() {
    val state = MutableStateFlow(initialState)
    val stateMutex = Mutex()

    /**
     * Returns a snapshot of the current state.
     */
    val currentState: S
        get() = state.value

    val stateFlow: StateFlow<S>
        get() = state

    protected fun <T> Flow<T>.onEachSetState(reducer: S.(T) -> S): Flow<T> {
        return onEach { item -> setState { reducer(item) } }
    }

    protected suspend fun <T> Flow<T>.collectAndSetState(reducer: S.(T) -> S) {
        return collectLatest { item -> setState { reducer(item) } }
    }

    protected fun subscribe(block: (S) -> Unit) {
        viewModelScope.launch {
            state.collect { block(it) }
        }
    }

    protected fun <A> selectSubscribe(prop1: KProperty1<S, A>, block: (A) -> Unit) {
        viewModelScope.launch {
            selectSubscribe(prop1).collect { block(it) }
        }
    }

    private fun <A> selectSubscribe(prop1: KProperty1<S, A>): Flow<A> {
        return state.map { prop1.get(it) }.distinctUntilChanged()
    }

    fun <A, B> selectSubscribe(
        prop1: KProperty1<S, A>,
        prop2: KProperty1<S, B>,
        block: (A, B) -> Unit
    ) {
        viewModelScope.launch {
            combine(selectSubscribe(prop1), selectSubscribe(prop2)) { prop1, prop2 ->
                block(prop1, prop2)
            }.collect {

            }
        }
    }

    protected suspend fun setState(reducer: S.() -> S) {
        stateMutex.withLock {
            state.value = reducer(state.value)
        }
    }

    protected fun CoroutineScope.launchSetState(reducer: S.() -> S) {
        launch { this@ReduxViewModel.setState(reducer) }
    }

    protected suspend fun withState(block: (S) -> Unit) {
        stateMutex.withLock {
            block(state.value)
        }
    }

    protected fun CoroutineScope.withState(block: (S) -> Unit) {
        launch { this@ReduxViewModel.withState(block) }
    }
}

fun ViewModel.viewModelScoped(block: suspend () -> Unit) {
    viewModelScope.launch { block() }
}
